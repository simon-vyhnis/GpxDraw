import java.awt.*
import javax.swing.JFrame


    fun main(){
        val gpxRoute = GpxRoute("example_data/klasika.gpx")
        val canvas = object : Canvas() {
            override fun paint(g: Graphics) {
                // set color to red
                g.color = Color.red
                g.drawRect(0,0,10,10)
                gpxRoute.draw(g)
            }
        }

        val frame = JFrame("GpxDraw")
        frame.contentPane.add(canvas, BorderLayout.CENTER)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.size = Dimension(500, 500)
        frame.setLocationRelativeTo(null)
        frame.isVisible = true

    }
