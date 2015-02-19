#! /bin/sh

printf "class name to regex: "
read fname

mkdir -p org/kagelabs/hydrogen/stl
cp bin/org/kagelabs/hydrogen/stl/$name* org/kagelabs/hydrogen/stl
jar cf $fname org
mv $fname $fname.jar
rm -r org/
printf "created jar "
printf $fname.jar
printf "\n"

