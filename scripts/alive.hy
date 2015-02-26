
// this is a test
import org.kagelabs.hydrogen.stl.io
import org.kagelabs.hydrogen.stl.sys
import org.kagelabs.hydrogen.stl.math
import org.kagelabs.hydrogen.stl.string

$n = "hi"
$n = "hello"
io.print $n

label 1

$msg = "hello world! i'm alive!\nAnd I can use escape characters!"
io.print $msg

$date % sys.xcall "date"

$date % str.cat "current date: " $date
io.print $date

io.print "what's your name: "
$name % io.read
$name % str.cat "Hello, " $name 
io.print $name
if $name == "Patrick"
goto 1

#c = 10
#d % + #c 120
#c = #d

io.write "10 + 120 = "
io.print #c