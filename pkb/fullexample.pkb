// by default imports use org.kagelabs.pkb_aux
import io
// contains print, write, read

print "Hello World!"

write "What's your name? : "
read $name

#num = 12
$num = "12"

write "Hello, "
write $name
print "!"

<<<<<<< HEAD
if ($name == "Patrick")
goto 12

goto 13

label 12
print "hi patrick"

label 13
abort
=======
$tmp = "Patrick"
if $name == $tmp
print "hello!"
end 
>>>>>>> 97b33880cdb943534aaf808112bccc27397cdf64
