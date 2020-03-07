package com.maxl.scraper

object TweetScraper {
  val defaultTimeOut: Int = 5000
  val requestMethod: String = "GET"
  val userAgent: String = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; Trident/7.0; rv:11.0) like Gecko"


  // Predefined from Example, no unit tests conducted
  // Source: https://alvinalexander.com/scala/how-to-write-scala-http-get-request-client-source-fromurl
  // Documentation:
  /**
   * Returns the text (content) from a REST URL as a String.
   * Inspired by http://matthewkwong.blogspot.com/2009/09/scala-scalaiosource-fromurl-blockshangs.html
   * and http://alvinalexander.com/blog/post/java/how-open-url-read-contents-httpurl-connection-java
   *
   * The `connectTimeout` and `readTimeout` comes from the Java URLConnection
   * class Javadoc.
   * @param url The full URL to connect to.
   * @param connectTimeout Sets a specified timeout value, in milliseconds,
   * to be used when opening a communications link to the resource referenced
   * by this URLConnection. If the timeout expires before the connection can
   * be established, a java.net.SocketTimeoutException
   * is raised. A timeout of zero is interpreted as an infinite timeout.
   * Defaults to 5000 ms.
   * @param readTimeout If the timeout expires before there is data available
   * for read, a java.net.SocketTimeoutException is raised. A timeout of zero
   * is interpreted as an infinite timeout. Defaults to 5000 ms.
   *
   * REMOVED: @param requestMethod Defaults to "GET". (Other methods have not been tested.)
   *
   * @example get("http://www.example.com/getInfo")
   * @example get("http://www.example.com/getInfo", 5000)
   * @example get("http://www.example.com/getInfo", 5000, 5000)
   */
  @throws(classOf[java.io.IOException])
  @throws(classOf[java.net.SocketTimeoutException])
  def get(url: String, connectTimeout: Int = defaultTimeOut, readTimeout: Int = defaultTimeOut) = {
    import java.net.{URL, HttpURLConnection}

    val connection = (new URL(url)).openConnection.asInstanceOf[HttpURLConnection]
    connection.setConnectTimeout(connectTimeout)
    connection.setReadTimeout(readTimeout)
    connection.setRequestMethod(requestMethod)
    connection.setRequestProperty("User-Agent", userAgent)

    val inputStream = connection.getInputStream
    val content = io.Source.fromInputStream(inputStream).mkString
    if (inputStream != null) inputStream.close
    content
  }
}
