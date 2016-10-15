setwd("C:\Users\windows7\Desktop\JingyLiu\db")
library(Rblpapi)
blpConnect()

##function: return the var of an indicator
#input: the index of an indicator
#output: the variance of this indicator over the past 10y
getVar <- function(index ="CARSCHNG Index" ){
  df <- bdh(index,c("BN_SURVEY_MEDIAN","ACTUAL_RELEASE"),start.date = Sys.Date()-365*10)
  df_filtered <- df[complete.cases(df), ]
  df_filtered$diff <- (df_filtered$ACTUAL_RELEASE-df_filtered$BN_SURVEY_MEDIAN)
  mean(df_filtered$diff)
}

#main
#read Indices.csv
indices <- read.csv("Indices.csv")
#get var for each index
vars <- sapply(indices$Index,getVar)
#write var into var.csv
result_df <- data.frame(indices$Index, vars)
write.csv(result_df, file = "vars.csv")