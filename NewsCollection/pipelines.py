# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: http://doc.scrapy.org/en/latest/topics/item-pipeline.html


# class NewscollectionPipeline(object):
    # fullname = 'news.data'
    # id = 0
    #
    # def process_item(self, item, spider):
    #     for element in ("url", "source", "title", "editor", "time", "content"):
    #         if item[element] is None:
    #             print "invalid items url: %s" % str(item["url"])
    #             return item
    #     self.fs = open(self.fullname, 'w+')
    #
    #     self.fs.write("news id: %s" % self.id)
    #     self.fs.write("\n")
    #     self.id += 1
    #     self.fs.write("url: %s" % item["url"][0].strip().encode('UTF-8'))
    #     self.fs.write("\n")
    #     self.fs.write("source: %s" % item["source"][0].strip().encode('UTF-8'))
    #     self.fs.write("\n")
    #     self.fs.write("title: %s" % item["title"][0].strip().encode('UTF-8'))
    #     self.fs.write("\n")
    #     self.fs.write("editor: %s" % item["editor"][0].strip().
    #                   encode('UTF-8').split('：')[1])
    #     self.fs.write("\n")
    #     time_string = item["time"][0].strip().split()
    #     datetime = time_string[0] + ' ' + time_string[1]
    #     self.fs.write("time: %s" % datetime.encode('UTF-8'))
    #     self.fs.write("\n")
    #     content = ""
    #     for para in item["content"]:
    #         content += para.strip().replace('\n', '').replace('\t', '')
    #     self.fs.write("content: %s" % content.encode('UTF-8'))
    #     self.fs.write("\n")
    #
    #     self.fs.flush()
    #     self.fs.close()
    #
    #     return item



    # def process_item(self, item, spider):
    #     for element in ("url", "source", "title", "editor", "time", "content"):
    #         if item[element] is None:
    #             print "invalid items url: %s" % str(item["url"])
    #             return item
    #     print "url: %s" % item["url"][0].strip().encode('UTF-8')
    #     print "source: %s" % item["source"][0].strip().encode('UTF-8')
    #     print "title: %s" % item["title"][0].strip().encode('UTF-8')
    #     print "editor: %s" % item["editor"][0].strip().encode('UTF-8').split('：')[1]
    #     time_string = item["time"][0].strip().split()
    #     datetime = time_string[0] + ' ' + time_string[1]
    #     print "time: %s" % datetime.encode('UTF-8')
    #     # content = ""
    #     # for para in item["content"]:
    #     #     content += para.strip().replace('\n', '').replace('\t', '')
    #     # print "content: %s" % content.encode('UTF-8')
    #     return item
