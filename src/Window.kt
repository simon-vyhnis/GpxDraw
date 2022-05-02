import java.awt.*
import javax.swing.JFrame


    fun main(){
        val gpxRoute = GpxRoute("example_data/kratka.gpx")
        val canvas = object : Canvas() {
            override fun paint(g: Graphics) {
                // set color to red
                g.color = Color.green
                gpxRoute.draw(g)
            }
        }

        val frame = JFrame("GpxDraw")
        frame.contentPane.add(canvas, BorderLayout.CENTER)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.size = Dimension(500, 500)
        frame.setLocationRelativeTo(null)
        canvas.addMouseMotionListener(gpxRoute)
        frame.isVisible = true
        while(true){
            canvas.repaint()
            Thread.sleep(100)
        }

    }
