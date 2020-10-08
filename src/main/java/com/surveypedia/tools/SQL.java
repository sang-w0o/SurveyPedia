package com.surveypedia.tools;

public class SQL {

    public class SurveyInfo {

        public static final String SURVEY_INFO_BY_DEAD_LINE = "SELECT nick writer, s_title, ifnull(0, interest_count), DATE(writtendate) written_date, DATE(end_date) end_date, sub1.s_code s_code FROM \n" +
                "(SELECT A.s_code, C.nick, A.s_title, A.writtendate, A.s_reported, DATE_ADD(A.writtendate, INTERVAL D.g_deadLine DAY) end_date,\n" +
                "        A.s_public, D.g_sample_num, D.g_deadline FROM survey A, members C, grades D WHERE A.email = C.email AND C.g_name = D.g_name) sub1\n" +
                "        LEFT join (SELECT s_code, COUNT(s_code) interest_count FROM interests GROUP BY s_code) sub2 \n" +
                "\t\tON (sub1.s_code = sub2.s_code) LEFT join (SELECT s_code, COUNT(s_code) sample_num from pointhistory\n" +
                "\t\tWHERE ph_type='P' GROUP by s_code) sub3 ON (sub1.s_code = sub3.s_code) WHERE\n" +
                "        sub1.s_reported != 'Y' AND g_sample_num - IFNULL(sample_num, 0) != 0\n" +
                "        AND DATE(DATE_ADD(sub1.writtendate, INTERVAL sub1.g_deadLine DAY)) > DATE(NOW())\n" +
                "\t\tORDER by TIMESTAMPDIFF(DAY, NOW(), end_date) asc LIMIT 10;";

        public static final String SURVEY_INFO_BY_SPARE_SAMPLE_NUM = "select sub1.s_code s_code, nick writer, s_title, ifnull(0, interest_count), DATE(end_date) end_date,\n" +
                "CAST(g_sample_num - IFNULL(sample_num, 0) AS SIGNED INTEGER) spare_sample_num FROM \n" +
                "(SELECT A.s_code, C.nick, A.s_title, A.writtendate, A.s_reported, DATE_ADD(A.writtendate, INTERVAL D.g_deadLine DAY) end_date,\n" +
                "D.g_sample_num, A.s_public, D.g_deadline FROM survey A, members C, grades D WHERE A.email = C.email AND C.g_name = D.g_name) sub1 \n" +
                "LEFT JOIN (SELECT s_code, COUNT(s_code) interest_count FROM interests GROUP BY s_code) sub2 ON (sub1.s_code = sub2.s_code)\n" +
                "LEFT join (SELECT s_code, COUNT(s_code) sample_num FROM\tpointhistory WHERE ph_type='P' GROUP by s_code) sub3 \n" +
                "ON (sub1.s_code = sub3.s_code) where sub1.s_reported != 'Y' AND g_sample_num - IFNULL(sample_num, 0) != 0 \n" +
                "AND DATE(DATE_ADD(sub1.writtendate, INTERVAL sub1.g_deadLine DAY)) > DATE(NOW())\n" +
                "ORDER by g_sample_num - IFNULL(sample_num, 0) LIMIT\t10;";

        public static final String SURVEY_INFO_BY_END_SURVEY = "SELECT\r\n" + "		sub1.s_code s_code,\r\n" + "		nick writer, \r\n"
                + "		s_title, \r\n" + "		interest_count, \r\n" + "		DATE(end_date) end_date,\r\n"
                + "		((IFNULL(sample_num, 0) * 2) + interest_count) price\r\n" + "FROM (SELECT \r\n"
                + "				A.s_code, \r\n" + "				C.nick, \r\n" + "				A.s_title, \r\n"
                + "				A.writtendate,\r\n" + "				A.s_reported,\r\n"
                + "				D.g_sample_num,\r\n"
                + "				DATE_ADD(A.writtendate, INTERVAL D.g_deadLine DAY) end_date,\r\n"
                + "				A.s_public\r\n" + "		FROM survey A, members C, grades D \r\n"
                + "		WHERE A.email = C.email AND C.g_name = D.g_name) sub1 \r\n" + "		LEFT JOIN \r\n"
                + "	  (SELECT \r\n" + "	  			s_code, \r\n" + "				COUNT(s_code) interest_count\r\n"
                + "		FROM interests \r\n" + "		GROUP BY s_code) sub2 ON (sub1.s_code = sub2.s_code)\r\n"
                + "		LEFT JOIN\r\n" + "		(SELECT \r\n" + "			s_code, COUNT(s_code) sample_num\r\n"
                + "		FROM\r\n" + "			pointhistory\r\n" + "		WHERE ph_type='P'\r\n"
                + "		GROUP BY\r\n" + "			s_code) sub3 ON (sub1.s_code = sub3.s_code)\r\n" + "WHERE \r\n"
                + "		sub1.s_reported != 'Y'\r\n" + "		AND sub1.s_public = 'Y'\r\n"
                + "		AND (g_sample_num - IFNULL(sample_num, 0) = 0\r\n"
                + "		OR DATE(NOW()) >= DATE(end_date))\r\n" + "ORDER BY\r\n"
                + "	TIMESTAMPDIFF(DAY, NOW(), end_date) DESC\r\n" + "LIMIT\r\n" + "	10;";

        public static final String SURVEY_ENDED_LIST = "select sub1.s_code s_code, nick writer, email, s_title, sub4.c_desc,\n" +
                "IFNULL(sample_num, 0) spare_sample_num,\n" +
                "((IFNULL(sample_num, 0) * 2) + interest_count) price FROM \n" +
                "(SELECT A.s_code, A.email, C.nick, A.s_title, A.writtendate, A.c_code, DATE_ADD(A.writtendate, INTERVAL D.g_deadLine DAY) end_date,\n" +
                "\t\tD.g_sample_num, A.s_public,\tD.g_deadline, A.s_reported FROM survey A, members C, grades D \n" +
                "\t\tWHERE A.email = C.email AND C.g_name = D.g_name) sub1 \n" +
                "\t\tLEFT join (SELECT s_code, COUNT(s_code) interest_count FROM interests GROUP BY s_code) sub2 ON (sub1.s_code = sub2.s_code)\n" +
                "\t\tLEFT join (SELECT s_code, COUNT(s_code) sample_num FROM pointhistory WHERE ph_type='P'\n" +
                "\t\tGROUP BY s_code) sub3 ON (sub1.s_code = sub3.s_code)\n" +
                "\t\tLEFT join (select c_code, c_desc FROM categories) sub4 ON (sub1.c_code = sub4.c_code)\n" +
                "where sub1.s_public = 'Y' AND (g_sample_num - IFNULL(sample_num, 0) = 0\n" +
                "\t\tOR DATE(NOW()) >= DATE(end_date)) AND sub1.s_reported = 'N';";

        public static final String SURVEY_COUNT_BY_CATEGORY = "SELECT COUNT(*) FROM \n" +
                "(SELECT A.s_code, A.writtendate, A.s_reported, C.g_deadline, C.g_sample_num, DATE_ADD(A.writtendate, INTERVAL C.g_deadLine DAY) end_date \n" +
                "FROM survey A, members B, grades C WHERE A.email = B.email AND A.c_code=? AND B.g_name = C.g_name) sub1 \n" +
                "LEFT JOIN (SELECT s_code, COUNT(s_code) interest_count FROM interests GROUP BY s_code) sub2 ON(sub1.s_code = sub2.s_code) \n" +
                "LEFT join (SELECT s_code, COUNT(s_code) sample_num FROM pointhistory WHERE ph_type='P' \n" +
                "GROUP BY s_code) sub3 ON (sub1.s_code = sub3.s_code) where \n" +
                "sub1.s_reported != 'Y' AND g_sample_num - IFNULL(sample_num, 0) != 0 \n" +
                "AND DATE(DATE_ADD(sub1.writtendate, INTERVAL sub1.g_deadLine DAY)) > DATE(NOW());";

        public static final String SURVEY_LIST_BY_CATEGORY = "SELECT sub1.s_code s_code, nick writer, s_title, CAST(writtendate AS DATE) written_date, IFNULL(interest_count, 0)\n" +
                "FROM (SELECT A.s_code, B.nick, A.s_title, A.writtendate, A.s_reported, A.s_id, C.g_deadline, C.g_sample_num,\n" +
                "DATE_ADD(A.writtendate, INTERVAL C.g_deadLine DAY) end_date\t\n" +
                "FROM survey A, members B, grades C WHERE A.email = B.email AND A.c_code=? AND B.g_name = C.g_name) sub1\n" +
                "LEFT JOIN (SELECT s_code, COUNT(s_code) interest_count FROM interests GROUP BY s_code) sub2 ON(sub1.s_code = sub2.s_code) \n" +
                "LEFT join (SELECT s_code, COUNT(s_code) sample_num from pointhistory WHERE ph_type='P'\n" +
                "GROUP by s_code) sub3 ON (sub1.s_code = sub3.s_code) where sub1.s_reported != 'Y'\n" +
                "AND g_sample_num - IFNULL(sample_num, 0) != 0 AND DATE(DATE_ADD(sub1.writtendate, INTERVAL sub1.g_deadLine DAY)) > DATE(NOW()) \n" +
                "ORDER by TIMESTAMPDIFF(DAY, NOW(), end_date) asc limit ?, ?;";
    }

    public class Members {

        public static final String MEMBER_PASS_UPDATE = "UPDATE members SET pass=PASSWORD(?) WHERE email=?";
        public static final String MEMBER_SIGNUP = "INSERT INTO members VALUES(?, PASSWORD(?), ?, DEFAULT, 'IRON')";
        public static final String MEMBER_POINT = "SELECT IFNULL(SUM(p.pointchange), 0) FROM members m NATURAL JOIN pointhistory p WHERE m.email = ?";
    }

    public class Survey {

        public static final String SURVEY_HISTORY_BY_CODE = "SELECT s_code, s_title, email, c_code FROM survey WHERE s_code=?";
        public static final String SURVEY_WRITE_COUNT = "SELECT COUNT(*) FROM survey WHERE email=?";
        public static final String SURVEY_PARTICIPATE_COUNT = "SELECT COUNT(*) FROM pointhistory WHERE email=? AND ph_type='P'";
        public static final String SURVEY_LAST_S_ID = "SELECT s_id FROM survey WHERE email=? AND s_reported != 'Y' ORDER BY s_code DESC LIMIT 1;";
    }

    public class PointHistory {
        public static final String GET_POINT_BY_EMAIL = "SELECT SUM(B.pointchange) AS point FROM members AS A NATURAL JOIN pointhistory AS B WHERE A.email = ?";
        public static final String GET_SAMPLE_COUNT_BY_S_CODE = "SELECT count(*) FROM pointhistory WHERE s_code =? AND pointchange = 5 AND ph_type='P'";
    }

    public class Category {
        public static final String GET_DES_BY_CODE = "SELECT c_desc FROM categories WHERE c_code = ?";
    }

    public class Interest {
        public static final String GET_COUNT_BY_S_CODE = "SELECT COUNT(*) FROM interests WHERE s_code=?";
    }
}
