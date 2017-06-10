# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# http://doc.scrapy.org/en/latest/topics/items.html

import scrapy

class NewsItem(scrapy.Item):
    # url of original news website
    url = scrapy.Field()
    # the real information it is from
    source = scrapy.Field()
    # title of news
    title = scrapy.Field()
    # deliver time
    time = scrapy.Field()
    # content of news
    content = scrapy.Field()
    # types of news
    types = scrapy.Field()
    # usually is the name of table
    # or the region of website it from
    newsType = scrapy.Field()
