#!/bin/bash
#==============================================================================
#
#        Filename:  @(#)build.sh
#
#     Description:  build hello clojure
#
#         Version:  1.0
#         Created:  2018/03/05 04:35:23
#         Changed:  <vinurs 03/05/2018 12:35:38>
#        Revision:  none
#
#          Author:  vinurs
#           Email:  <vinurs@vinurs-MacBook-Pro.local>
#
#==============================================================================
##

lein clean
lein uberjar
