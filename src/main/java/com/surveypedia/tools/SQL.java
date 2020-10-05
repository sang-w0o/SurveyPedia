package com.surveypedia.tools;

public class SQL {

    public class SurveyInfo {

        public static final String SURVEY_INFO_BY_DEAD_LINE = "SELECT \r\n" + "		nick writer, \r\n" + "		s_title, \r\n" + "		interest_count, \r\n"
                + "		DATE(writtendate) written_date, \r\n" + "		DATE(end_date) end_date,\r\n"
                + "		sub1.s_code s_code\r\n" + "FROM (SELECT \r\n" + "				A.s_code, \r\n"
                + "				C.nick, \r\n" + "				A.s_title, \r\n" + "				A.writtendate, \r\n"
                + "				A.s_reported, \r\n"
                + "				DATE_ADD(A.writtendate, INTERVAL D.g_deadLine DAY) end_date,\r\n"
                + "				A.s_public,\r\n" + "				D.g_sample_num, \r\n"
                + "				D.g_deadline\r\n" + "		FROM survey A, members C, grades D \r\n"
                + "		WHERE A.email = C.email AND C.g_name = D.g_name) sub1 \r\n" + "		LEFT JOIN \r\n"
                + "	  (SELECT \r\n" + "	  			s_code, \r\n" + "				COUNT(s_code) interest_count \r\n"
                + "		FROM interests \r\n" + "		GROUP BY s_code) sub2 \r\n"
                + "	   ON (sub1.s_code = sub2.s_code)\r\n" + "	   	LEFT JOIN\r\n" + "		(SELECT \r\n"
                + "			s_code, COUNT(s_code) sample_num\r\n" + "		FROM\r\n" + "			pointhistory\r\n"
                + "		WHERE ph_type='P'\r\n" + "		GROUP BY\r\n"
                + "			s_code) sub3 ON (sub1.s_code = sub3.s_code)\r\n" + "WHERE\r\n"
                + "	sub1.s_reported != 'Y'\r\n" + "	AND g_sample_num - IFNULL(sample_num, 0) != 0 \r\n"
                + "	AND DATE(DATE_ADD(sub1.writtendate, INTERVAL sub1.g_deadLine DAY)) > DATE(NOW())\r\n"
                + "ORDER BY\r\n" + "	TIMESTAMPDIFF(DAY, NOW(), end_date) ASC\r\n" + "LIMIT \r\n" + "	10;";

        public static final String SURVEY_INFO_BY_SPARE_SAMPLE_NUM = "SELECT\r\n" + "		sub1.s_code s_code,\r\n" + "		nick writer, \r\n"
                + "		s_title, \r\n" + "		interest_count, \r\n" + "		DATE(end_date) end_date,\r\n"
                + "		CAST(g_sample_num - IFNULL(sample_num, 0) AS SIGNED INTEGER) spare_sample_num\r\n"
                + "FROM (SELECT \r\n" + "				A.s_code, \r\n" + "				C.nick, \r\n"
                + "				A.s_title, \r\n" + "				A.writtendate,\r\n"
                + "				A.s_reported,\r\n"
                + "				DATE_ADD(A.writtendate, INTERVAL D.g_deadLine DAY) end_date,\r\n"
                + "				D.g_sample_num,\r\n" + "				A.s_public,\r\n"
                + "				D.g_deadline\r\n" + "		FROM survey A, members C, grades D \r\n"
                + "		WHERE A.email = C.email AND C.g_name = D.g_name) sub1 \r\n" + "		LEFT JOIN \r\n"
                + "	  (SELECT \r\n" + "	  			s_code, \r\n" + "				COUNT(s_code) interest_count\r\n"
                + "		FROM interests \r\n" + "		GROUP BY s_code) sub2 ON (sub1.s_code = sub2.s_code)\r\n"
                + "		LEFT JOIN\r\n" + "		(SELECT \r\n" + "			s_code, COUNT(s_code) sample_num\r\n"
                + "		FROM\r\n" + "			pointhistory\r\n" + "		WHERE ph_type='P'\r\n"
                + "		GROUP BY\r\n" + "			s_code) sub3 ON (sub1.s_code = sub3.s_code)\r\n" + "WHERE\r\n"
                + "	sub1.s_reported != 'Y'\r\n" + "	AND g_sample_num - IFNULL(sample_num, 0) != 0\r\n"
                + "	AND DATE(DATE_ADD(sub1.writtendate, INTERVAL sub1.g_deadLine DAY)) > DATE(NOW())\r\n"
                + "ORDER BY\r\n" + "	g_sample_num - IFNULL(sample_num, 0)\r\n" + "LIMIT\r\n" + "	10;";

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
    }

    public class Members {

        public static final String MEMBER_PASS_UPDATE = "UPDATE members SET pass=PASSWORD(?) WHERE email=?";
        public static final String MEMBER_SIGNUP = "INSERT INTO members VALUES(?, PASSWORD(?), ?, DEFAULT, 'IRON')";
        public static final String MEMBER_POINT = "SELECT SUM(p.pointchange) FROM members m NATURAL JOIN pointhistory p WHERE m.email = ?";
    }
}
