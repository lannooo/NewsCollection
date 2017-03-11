from NewsCollection.items import NewsItem
from scrapy.spiders import CrawlSpider, Rule
from scrapy.linkextractors import LinkExtractor
import re
import sys

class NewsSpider(CrawlSpider):
    name = "tech163"
    allowed_domain = ["tech.163.com"]
    start_urls = ["http://tech.163.com"]

    rules = [
        Rule(LinkExtractor(allow='tech.163.com/\d{2}/\d{4}/\d{2}/.*\.html'),
             follow=False, callback='parse_item'),
    ]

    def parse_item(self, response):
        self.logger.debug("parse func: %s" % sys._getframe().f_code.co_name)
        item = NewsItem()
        item['url'] = [response.url]
        item['source'] = \
            response.xpath('//a[@id="ne_article_source"]/text()'). \
                extract()
        item['title'] = \
            response.xpath('//div[@class="post_content_main"]/h1/text()'). \
                extract()
        item['editor'] = \
            response.xpath('//span[@class="ep-editor"]/text()'). \
                extract()
        item['time'] = \
            response.xpath('//div[@class="post_time_source"]/text()'). \
                extract()
        item['content'] = \
            response.xpath('//div[@class="post_text"]/p/text()'). \
                extract()
        for key in item:
            for data in item[key]:
                self.logger.debug("item %s value %s" % (key, data))
        return item
