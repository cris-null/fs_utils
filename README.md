# fs_utils
Filesystem utils that will benefit me personally. Made in order to learn more about Kotlin. 

# Commands

* info <path> - print debug info of file/dir
* copy <path to file> - copies a file into a dir
* print-adj-dirs <path to file> - prints the names of directories adjacent to this file
* mass-copy <path to file> - copies a file into all adjacent directories
* verify <path to file> - checks if all adjacent directories to file contain a copy of it, and prints the result
* mass-del <path to file> - checks all adjacent directories to file for copies of it, and deletes them if found
* simplify <path to dir> - alter the filenames of all files contain inside dir, and makes them lowercase and with underscores for spaces
* prefix <path to dir> - prefixes all the filenames inside dir with a string
* remove--prefix <path to dir> - reverse operation of prefix command
* remove-char <path to dir> - checks the filenames of all files inside dir, and removes all occurrences of char
