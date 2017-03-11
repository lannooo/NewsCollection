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
        if item['types'] is None:
            item['types'] = u"未知"
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
            log.msg("Duplicate item url:%s"%item['url'], level=log.WARNING)
            raise DropItem("Duplicate item found, url is: %s" % item['url'])
        else:
            self.urls_seen.add(item['url'])
            log.msg("passing DuplicatePipeline:%s"%item['url'], level=log.INFO)
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
    sql_fetch = 'select * from `%s` where `url`= "%s"'

    sql_insert = 'insert into `%s` (`url`, `source`, `title`, `time`, `content`, `types`) values ("%s", "%s", "%s", "%s", "%s", "%s")'

    def process_item(self, item, spider):
        db = MySQLdb.connect(DBWriterPipeline.host,
                             DBWriterPipeline.user,
                             DBWriterPipeline.passwd,
                             DBWriterPipeline.database)
        cursor = db.cursor()
        # add this or the chinese words would be wrong in Mysql
        cursor.execute("SET NAMES utf8")
        table = item['newsType']
        try:
            cursor.execute(self.sql_insert % (table, item['url'].encode('utf-8'),
                                              item['source'].encode('utf-8'),item['title'].encode('utf-8'),
                                              item['time'].encode('utf-8'),item['content'].encode('utf-8'),
                                              item['types'].encode('utf-8')))
            db.commit()
            log.msg("commit url: %s"%item['url'], level=log.INFO)
        except:
            db.rollback()
            log.msg("except for DBWriterPipeline", level=log.WARNING)
        finally:
            db.close()
            log.msg("passing DBWriterPipeline:%s" % item['url'], level=log.INFO)


    # def open_spider(self, spider):
    #     self.db = MySQLdb.connect(DBWriterPipeline.host,
    #                               DBWriterPipeline.user,
    #                               DBWriterPipeline.passwd,
    #                               DBWriterPipeline.database)
    #     log.msg("call open_spider...", level=log.INFO)
    #
    # def close_spider(self, spider):
    #     log.msg("call close_spider...", level=log.INFO)
    #     self.db.close()
