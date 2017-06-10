# -*- coding: utf-8 -*-

def getImgTags(imgsArray):
    """
    surround the img src attributes with tag:<img src="(...)">
    as well as the tag <p>...</p>
    :param imgsArray:
    :return:
    """
    result = ""
    for each in imgsArray:
        result += '<img src="' + each + '">'
    return '<p>' + result + '</p>'


def getPTags(textArray):
    """
    surround all the text in tag <p>
    including the text in the sub node under <p>
    :param textArray:
    :return:
    """
    result = ""
    if len(textArray) > 0:
        for each in textArray:
            result += each.strip('\n').strip()
    else:
        pass
    return '<p>' + result + '</p>'