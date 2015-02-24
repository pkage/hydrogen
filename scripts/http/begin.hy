import org.kagelabs.hydrogen.stl.io

print "configuring server..."

addroute "/" "scripts/http/index.hy"

startserver
