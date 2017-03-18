# -*- coding: utf-8 -*-
from scrapy import log
from scrapy.exceptions import DropItem

import MySQLdb.cursors

class RemoveEmptyPipeline(object):
    """
    remove the items which are not complete
    """
    def process_item(self, item, spider):
        if item['content'] is None or len(item['content'])==0:
            log.msg("content not valid:%s"%item['content'], level=log.WARNING)
            raise DropItem("Empty item found, url is: %s" % item['url'])
        if item['title'] is None or len(item['title'])==0:
            log.msg("title not valid:%s" % item['title'], level=log.WARNING)
            raise DropItem("Empty item found, url is: %s" % item['url'])
        if item['source'] is None:
            item['source'] = u"未知"
        if item['time'] is None:
            item['time'] = u"未知"
        log.msg("passing RemoveEmptyPipeline:%s" % item['url'], level=log.INFO)

        return item

class DuplicatePipeline(object):
    """
    remove the duplicate items
    """
    def __init__(self):
        self.urls_seen = set()

    def process_item(self, item, spider):
        if item['url'] in self.urls_seen:
            log.msg("Duplicate item url:%s" % item['url'], level=log.WARNING)
            raise DropItem("Duplicate item found, url is: %s" % item['url'])
        else:
            self.urls_seen.add(item['url'])
            log.msg("passing DuplicatePipeline:%s" % item['url'], level=log.INFO)
            return item


class DBWriterPipeline(object):
    """
    A pipeline to write into mysql DB
    """
    # class variables
    host = "localhost"
    user = "root"
    passwd = "root"
    database = "news"
    # sql_fetch = 'select * from `%s` where `url`= "%s"'

    def process_item(self, item, spider):
        cursor = self.conn.cursor()
        # add this or the chinese words would be wrong in Mysql
        # cursor.execute("SET NAMES utf8")
        table = item['newsType']
        sql_insert = """insert into """ + table + """(`url`, `source`, `title`,
                    `time`, `content`, `types`) values (%s, %s, %s, %s, %s, %s)"""
        try:
            cursor.execute(sql_insert, (item['url'], item['source'],
                                        item['title'], item['time'],
                                        item['content'], item['types']))
            self.conn.commit()
            log.msg("successfully commit url: %s" % item['url'], level=log.INFO)
        except MySQLdb.Error, e:
            print "MySQLdb.Error %d: %s" % (e.args[0], e.args[1])
            self.conn.rollback()
            log.msg("except for DBWriterPipeline", level=log.WARNING)
        finally:
            log.msg("passing DBWriterPipeline, content len=%d"%len(item['content']), level=log.INFO)
            # return item

    def open_spider(self, spider):
        log.msg("call open_spider...", level=log.INFO)
        self.conn = MySQLdb.connect(user='root', passwd='root', db='news',
                                    host='localhost', charset='utf8', use_unicode=True)

    def close_spider(self, spider):
        log.msg("call close_spider...", level=log.INFO)
        self.conn.close()
