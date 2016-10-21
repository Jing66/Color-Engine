#!/usr/bin/env Rscript
library(Rblpapi)
blpConnect()

##function: return the var of an indicator
#input: the index of an indicator
#output: the variance of this indicator over the past 10y
getVar <- function(index ="CARSCHNG Index" ){
  df <- bdh(index,c("BN_SURVEY_MEDIAN","ACTUAL_RELEASE"),start.date = Sys.Date()-365*10)
  df_filtered <- df[complete.cases(df), ]
  df_filtered$diff <- (df_filtered$ACTUAL_RELEASE-df_filtered$BN_SURVEY_MEDIAN)
  abs_diff <- sapply(df_filtered$diff, abs)
  mean(abs_diff)
  
}

getName <- function(index){
  df <- bdp(index, c("NAME"))
  df$NAME
}

#main
setwd("C:/Users/windows7/Desktop/JingyLiu/db")
#read Indices.csv
indices <- read.csv("Indices.csv")
#get var for each index
indicators <- sapply(indices$Index,as.character)
vars <- sapply(indicators,getVar)
#write var into var.csv
names <- sapply(indicators,getName)
result_df <- data.frame(indicators,names, vars)
write.csv(result_df, file = "Vars.csv",row.names=FALSE)