import org.simpleframework.xml.Serializer
import org.simpleframework.xml.core.Persister
import java.awt.Graphics
import java.io.File

class GpxRoute(path: String){
    private val latLimits = DoubleLimits()
    private val lonLimits = DoubleLimits()
    private lateinit var points : List<Trkpt>

    init{
        val serializer: Serializer = Persister()
        val file = File(path)
        val data = serializer.read(GpxFile::class.java, file)
        points = data.trk.points


        for (point in points){
            point.lat?.let { latLimits.tryNew(it) }
            point.lon?.let { lonLimits.tryNew(it) }
        }
    }


    fun draw(g: Graphics) {
        val latScale = latLimits.getDelta()
        println(latScale)
        val lonScale = lonLimits.getDelta()

        val width = 500
        val height = 500
        val padding = 50

        for (point in points){

            g.drawRect(((point.lon!!-lonLimits.min!!)/lonScale*(width-2*padding)).toInt()+padding, ((point.lat!!-latLimits.min!!)/latScale*(height-2*padding)).toInt()+padding, 1,1)
            println(((point.lon!!-lonLimits.min!!)/lonScale*400).toInt())
        }

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

