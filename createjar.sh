#! /bin/sh

printf "class name to regex: "
read fname

mkdir -p org/kagelabs/hydrogen/stl
cp bin/org/kagelabs/hydrogen/stl/$fname* org/kagelabs/hydrogen/stl
jar cf $fname org
mv $fname $fname.jar
rm -r org/
printf "created jar "
printf $fname.jar
printf "\nmove to /usr/local/hydrogen/lib? (y/n): "
read move
if [ "$move" = "y" ]; then
	sudo mv $fname.jar /usr/local/hydrogen/lib/
fi

