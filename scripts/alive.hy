
// this is a test
import org.kagelabs.hydrogen.stl.io

$n = "hi"
$n = "hello"
print $n

label 1

$msg = "hello world! i'm alive!"
print $msg

print "what's your name: "
$name % read
write "Hello, "
print $name
if $name == "Patrick"
goto 1

print "exiting"