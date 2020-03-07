//Needed to read html:

interp.load.ivy(
  "com.lihaoyi" %
  s"ammonite-shell_${scala.util.Properties.versionNumberString}" %
  ammonite.Constants.version
)
@
val shellSession = ammonite.shell.ShellSession()
import shellSession._
import ammonite.ops._
import scala.collection.JavaConverters._

//Imports:
import $ivy.`org.jsoup:jsoup:1.12.1`
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

val filename = "sampleTweets.html"
val file = read(filename)
val doc = Jsoup.parse(file)
val eles = doc.getElementsByClass("content").asScala
for(ele: Element <- eles){
	val timeEle = ele.getElementsByClass("tweet-timestamp").first()
	val timeSpan = timeEle.getElementsByTag("span")
	val dataTime = timeSpan.attr("data-time-ms")

	val dataConversationId = timeEle.attr("data-conversation-id")

	val favoriteEle = ele.getElementsByClass("ProfileTweet-action--favorite").first()
	val favoriteSpan = favoriteEle.getElementsByTag("span")
	val favoriteCount = favoriteSpan.attr("data-tweet-stat-count")

	val replyEle = ele.getElementsByClass("ProfileTweet-action--reply").first()
	val replySpan = replyEle.getElementsByTag("span")
	val replyCount = replySpan.attr("data-tweet-stat-count")

	val retweetEle = ele.getElementsByClass("ProfileTweet-action--retweet").first()
	val retweetSpan = retweetEle.getElementsByTag("span")
	val retweetCount = retweetSpan.attr("data-tweet-stat-count")

	val tweetEle = ele.getElementsByClass("js-tweet-text-container").first()
	val tweetText = tweetEle.text()

	println("\n\n{")
	println(s"id:$dataConversationId")
	println(s"time:$dataTime")
	println(s"favorites:$favoriteCount")
	println(s"replies:$replyCount")
	println(s"retweets:$retweetCount")
	println(s"tweet:$tweetText")
	println("}\n\n")
	println(ele)
}
