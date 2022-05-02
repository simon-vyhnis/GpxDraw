import org.simpleframework.xml.Serializer
import org.simpleframework.xml.core.Persister
import java.awt.Graphics
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionListener
import java.io.File

class GpxRoute(path: String) : MouseMotionListener{
    private val latLimits = DoubleLimits()
    private val lonLimits = DoubleLimits()
    private var points = ArrayList<TrackPoint>()
    private val calibrationPoints = ArrayList<CalibrationPoint>()
    private var isPointSelected = false

    init{
        val serializer: Serializer = Persister()
        val file = File(path)
        val data = serializer.read(GpxFile::class.java, file)

        for (point in data.trk.points){
            point.lat?.let { latLimits.tryNew(it) }
            point.lon?.let { lonLimits.tryNew(it) }
        }

        val width = 500
        val height = 500
        val padding = 50

        for (point in data.trk.points){
            points.add(TrackPoint(
                ((point.lat!!-latLimits.min!!)/latLimits.getDelta()*(height-(2*padding))).toInt(),
                ((point.lon!!-lonLimits.min!!)/lonLimits.getDelta()*(width-(2*padding))).toInt()))
        }
    }


    fun draw(g: Graphics) {
        for(i: Int in 0..points.size-2){
            var point1 = points[i]
            var point2 = points[i+1]
            for(calibrationPoint in calibrationPoints){
                if(calibrationPoint.pos == i){
                    point1 = TrackPoint(calibrationPoint.x, calibrationPoint.y)
                }else if(calibrationPoint.pos == i+1){
                    point2 = TrackPoint(calibrationPoint.x, calibrationPoint.y)
                }
            }
            g.drawLine(point1.x, point1.y, point2.x, point2.y)
        }
    }

    override fun mouseDragged(e: MouseEvent?) {
        if (e != null) {
            if (isPointSelected) {
                calibrationPoints[calibrationPoints.size - 1].x = e.x
                calibrationPoints[calibrationPoints.size - 1].y = e.y
            } else {
                for (i: Int in 0 until points.size) {
                    if (points[i].x < e.x+5 && points[i].x > e.x-5 &&
                        points[i].y < e.y+5 && points[i].y > e.y-5){
                        calibrationPoints.add(CalibrationPoint(i, e.x, e.y))
                        isPointSelected = true
                    }
                }
            }
        }
    }

    override fun mouseMoved(e: MouseEvent?){
        isPointSelected = false
    }
}

class DoubleLimits{
    var max: Double? = null
    var min: Double? = null

    fun tryNew(value: Double){
        if(max == null || min == null){
            max = value
            min = value
        }else{
            if(max!! < value){
                max = value
            }
            if(min!! > value){
                min = value
            }
        }
    }

    fun getDelta(): Double{
        return max!!-min!!
    }
}

class TrackPoint(var x: Int, var y: Int)
class CalibrationPoint(var pos : Int, var x: Int, var y: Int)

