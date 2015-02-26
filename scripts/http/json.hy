import org.kagelabs.hydrogen.stl.io
import org.kagelabs.hydrogen.stl.sys

io.print "{"
io.write "  date: \""
$date % sys.xcall "date"
io.write $date
io.write "\"\n}"
