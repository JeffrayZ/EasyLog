### CRLF 这个参数看起来比较眼熟，它就是Win风格的换行符，意思就是使用CR LF这一对作为一行的结尾而不是Unix风格的LF

### DEFAULT 这个参数是默认，使用默认的方法来加密

### NO_PADDING 这个参数是略去加密字符串最后的”=”

### NO_WRAP 这个参数意思是略去所有的换行符（设置后CRLF就没用了）

### URL_SAFE 这个参数意思是加密时不使用对URL和文件名有特殊意义的字符来作为加密字符，具体就是以-和_取代+和/