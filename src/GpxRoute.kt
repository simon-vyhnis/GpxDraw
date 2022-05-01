import org.simpleframework.xml.Serializer
import org.simpleframework.xml.core.Persister
import java.awt.Graphics
import java.io.File

class GpxRoute(path: String){
    var maxLat: Double? = null
    var maxLon: Double? = null
    var minLat: Double? = null
    var minLon: Double? = null

    init{
        val serializer: Serializer = Persister()
        val file = File(path)
        val data = serializer.read(GpxFile::class.java, file)
        val points = data.trk.points
        for (point in points){
            println("Lat: ${point.lat} Lon: ${point.lon}")
        }
    }


    fun draw(g: Graphics) {

    }


}

class DoubleLimits(){
    var max: Double? = null
    var min: Double? = null

    fun tryNew(value: Double){
        if(max == null){
            max = value
        }
    }
}

