package com.example.sparklearn.spark;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;
import java.security.PrivilegedAction;
import java.util.Properties;


public class ExternalDataSet {

    public Dataset<Row>  getDateset(SparkSession session){

        Dataset<Row> dateset=null;
        Configuration configuration = new Configuration();
        configuration.set("hadoop.security.authentication", "Kerberos");
        String url = "jdbc:hive2://10.130.7.54:10001/stress;principal=hs2/hadoop10.novalocal@BONCST.LBHY;auth=kerberos";
        //    val mysqlData = session.read.jdbc(url, "DWA_D_USE_BASE_INFO_20200603", prop)
        try {
            UserGroupInformation UGI = null;
            UGI = UserGroupInformation.loginUserFromKeytabAndReturnUGI("hs2/hadoop10.novalocal@BONCST.LBHY", "/home/hadoop/zgh/waiconf/hs10.keytab");
            UGI.checkTGTAndReloginFromKeytab();
            System.setProperty("java.security.krb5.conf", "/home/hadoop/zgh/waiconf/krb5.conf");//.conf
            dateset= UGI.doAs(new PrivilegedAction<Dataset<Row>>() {
                @Override
                public Dataset<Row> run() {
                    return  session.read()
                            .format("jdbc")
                            .option("driver", "org.apache.hive.jdbc.HiveDriver")
                            .option("url", url)
                            .option("dbtable", "stress.step_step_22993425175_0")
                            .option("user", "hadoop")
                            .option("password", "")
                            .load();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
       return dateset;

    }

}
