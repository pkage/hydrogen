import org.kagelabs.hydrogen.stl.io

io.print "configuring server..."

http.addroute "/" "scripts/http/index.hy"

http.addroute "/api" "scripts/http/json.hy"

http.startserver
