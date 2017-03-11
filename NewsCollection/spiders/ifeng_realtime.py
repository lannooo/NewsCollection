import datetime
import scrapy
import md5
from scrapy.contrib.spiders import CrawlSpider, Rule
from scrapy.contrib.linkextractors import LinkExtractor

from NewsCollection.items import NewsItem

# It works well.
# Some news page cannot be perfectly resolved,
# but it's all right, because we have enough pages as well.
class IfengSpider(CrawlSpider):
    """
    spider for zixun in ifeng.com
    start from the most recent news list, the start url is listed in start_urls

    """
    # get the date of today
    today = datetime.date.today()
    strs = today.strftime("%Y%m%d")

    name = "ifeng"
    allowed_domains = ["news.ifeng.com"]
    # news list from ifeng
    start_urls = ["http://news.ifeng.com/listpage/11502/"+strs+"/1/rtlist.shtml"]
    rules = [
        Rule(LinkExtractor(allow=('news\.ifeng\.com/listpage/11502/'+strs+'/\d{1}/rtlist\.shtml',),
                           restrict_xpaths=('//div[@class="nextPage"]')), follow=True),

        Rule(LinkExtractor(allow=('news\.ifeng\.com/a/'+strs+'/\d{8}_0\.shtml',),
                           restrict_xpaths=('//div[@class="newsList"]/ul/li')),
                           callback='parse_item', follow=False)
    ]

    def getImgTags(self, imgsArray):
        """
        surround the img src attributes with tag:<img src="(...)">
        as well as the tag <p>...</p>
        :param imgsArray:
        :return:
        """
        result = ""
        for each in imgsArray:
            result += '<img src="' + each + '">'
        return 'p'+result+'</p>'

    def getPTags(self, textArray):
        """
        surround all the text in tag <p>
        including the text in the sub node under <p>
        :param textArray:
        :return:
        """
        result = ""
        if len(textArray)>0:
            for each in textArray:
                result += each
        else:
            pass
        return '<p>'+result+'</p>'

    def parse_item(self, response):
        """
        parse the response
        :param response:
        :return:
        """
        self.log("parse url %s." % response.url)
        item = NewsItem()
        # type 1 news: zixun in ifeng
        # by iterate all the p tags, to get the content of the news including img tags
        docContent = ""
        for eachP in response.xpath('//div[@id="main_content"]/p'):
            imgs = eachP.xpath('img/@src').extract()
            if(len(imgs) > 0):
                docContent += self.getImgTags(imgs)
            else:
                ptext = eachP.xpath('string(.)').extract()
                docContent += self.getPTags(ptext)
        item['content'] = docContent
        # it's ridiculous for the following code because the tag I need is sometimes different in some news pagesquit
        item['source'] = response.xpath('//span[@itemprop="publisher"]/span[@itemprop="name"]/text()').extract_first()
        if item['source'] is None:
            item['source'] = response.xpath('//span[@itemprop="publisher"]/span[@itemprop="name"]/a/text()').extract_first()
        item['time'] = response.xpath('//span[@itemprop="datePublished"]/text()').extract_first()
        item['title'] = response.xpath('//h1/text()').extract_first()
        item['types'] = response.xpath('//div[contains(@class, "js_crumb")]/a[2]/text()').extract_first()
        item['url'] = response.url
        item['newsType'] = "news_ifeng"

        return item



