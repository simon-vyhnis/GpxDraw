import org.simpleframework.xml.*

@Root(strict = false, name = "trkpt")
class Trkpt {
    @field:Attribute(name = "lat")
    var lat: Double? = null

    @field:Attribute(name = "lon")
    var lon: Double? = null
}

@Root(strict = false, name = "trk")
class Trk{
    @field:ElementList(name = "trkseg", required = false)
    lateinit var points: List<Trkpt>
}

@Root(strict = false, name = "gpx")
class GpxFile{
    @field:Element(name = "trk", required = false)
    lateinit var trk: Trk
}