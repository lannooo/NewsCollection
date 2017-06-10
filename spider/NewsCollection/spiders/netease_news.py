# -*- coding: utf-8 -*-

from scrapy.spiders import CrawlSpider, Rule
from scrapy.linkextractors import LinkExtractor

from NewsCollection.items import NewsItem
from NewsCollection.tool import tags

class NeteaseSpider(CrawlSpider):
    name = "netease"
    allowed_domains = ["163.com"]
    start_urls = ["http://news.163.com/rank/"]
    rules = [
        Rule(LinkExtractor(allow=('news\.163\.com/\d{2}/\d{4}/\d{2}/[A-Z0-9]+\.html',),
                           restrict_xpaths=('//div[contains(@class, "area-half")]')),
             callback='parse_news_item', follow=False),
        Rule(LinkExtractor(allow=('ent\.163\.com/\d{2}/\d{4}/\d{2}/[A-Z0-9]+\.html',),
                           restrict_xpaths=('//div[contains(@class, "area-half")]')),
             callback='parse_ent_item', follow=False),
        Rule(LinkExtractor(allow=('sports\.163\.com/\d{2}/\d{4}/\d{2}/[A-Z0-9]+\.html',),
                           restrict_xpaths=('//div[contains(@class, "area-half")]')),
             callback='parse_sports_item', follow=False),
        Rule(LinkExtractor(allow=('money\.163\.com/\d{2}/\d{4}/\d{2}/[A-Z0-9]+\.html',),
                           restrict_xpaths=('//div[contains(@class, "area-half")]')),
             callback='parse_money_item', follow=False),
        Rule(LinkExtractor(allow=('tech\.163\.com/\d{2}/\d{4}/\d{2}/[A-Z0-9]+\.html',),
                           restrict_xpaths=('//div[contains(@class, "area-half")]')),
             callback='parse_tech_item', follow=False),
        Rule(LinkExtractor(allow=('auto\.163\.com/\d{2}/\d{4}/\d{2}/[A-Z0-9]+\.html',),
                           restrict_xpaths=('//div[contains(@class, "area-half")]')),
             callback='parse_auto_item', follow=False),
        Rule(LinkExtractor(allow=('lady\.163\.com/\d{2}/\d{4}/\d{2}/[A-Z0-9]+\.html',),
                           restrict_xpaths=('//div[contains(@class, "area-half")]')),
             callback='parse_lady_item', follow=False),
        Rule(LinkExtractor(allow=('edu\.163\.com/\d{2}/\d{4}/\d{2}/[A-Z0-9]+\.html',),
                           restrict_xpaths=('//div[contains(@class, "area-half")]')),
             callback='parse_edu_item', follow=False),
    ]


    def parse_item(self, response, type):
        """
        parse the response with a generic method
        :param response:
        :param type: type of news
        :return:
        """
        self.log("parse url %s." % response.url)
        item = NewsItem()

        docContent = ""
        for eachP in response.xpath('//div[@id="endText"]//p'):
            style = eachP.xpath('style/text()').extract()
            if len(style)>0:
                continue

            imgs = eachP.xpath('img/@src').extract()
            if (len(imgs) > 0):
                docContent += tags.getImgTags(imgs)

            ptext = eachP.xpath('string(.)').extract()
            docContent += tags.getPTags(ptext)

        item['content'] = docContent
        item['source'] = response.xpath('//div[@class="post_time_source"]/a[1]/text()').extract_first()

        strTime = response.xpath('//div[@class="post_time_source"]/text()').extract_first()
        if strTime is None:
            item['time'] = None
        else:
            item['time'] = strTime.strip()[:19]

        item['title'] = response.xpath('//h1/text()').extract_first()
        item['url'] = response.url
        item['types'] = type
        item['newsType'] = "news_netease"

        return item

    def parse_news_item(self, response):
        type = response.xpath('//div[@class="post_crumb"]/a[3]/text()').extract_first()
        if type is None:
            type = u"新闻"
        return self.parse_item(response, type)


    def parse_ent_item(self, response):
        type = response.xpath('//div[@class="post_crumb"]/a[2]/text()').extract_first()
        if type is None:
            type = u"娱乐"
        return self.parse_item(response, type)

    def parse_sports_item(self, response):
        type = response.xpath('//div[@class="post_crumb"]/a[3]/text()').extract_first()
        if type is None:
            type = u"体育"
        return self.parse_item(response, type)

    def parse_money_item(self, response):
        type = response.xpath('//div[@class="post_crumb"]/a[3]/text()').extract_first()
        if type is None:
            type = u"财经"
        return self.parse_item(response, type)

    def parse_tech_item(self, response):
        type = response.xpath('//div[@class="post_crumb"]/a[3]/text()').extract_first()
        if type is None:
            type = u"科技"
        return self.parse_item(response, type)

    def parse_auto_item(self, response):
        type = response.xpath('//div[@class="post_crumb"]/a[3]/text()').extract_first()
        if type is None:
            type = u"汽车"
        return self.parse_item(response, type)

    def parse_lady_item(self, response):
        type = response.xpath('//div[@class="post_crumb"]/a[3]/text()').extract_first()
        if type is None:
            type = u"女性"
        return self.parse_item(response, type)

    def parse_edu_item(self, response):
        type = response.xpath('//div[@class="post_crumb"]/a[3]/text()').extract_first()
        if type is None:
            type = u"教育"
        return self.parse_item(response, type)