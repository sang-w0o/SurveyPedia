package com.surveypedia.tools;

public class SQL {

    public static class SurveyInfo {

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

        public static final String SURVEY_CURRENT_INFO_BY_EMAIL = "SELECT sub1.s_code s_code, nick writer, s_title, IFNULL(interest_count,0), DATE(writtendate) written_date, \n" +
                "DATE(end_date) end_date FROM (SELECT A.s_code, A.email, C.nick, A.s_title, A.writtendate, \n" +
                "A.s_reported, DATE_ADD(A.writtendate, INTERVAL D.g_deadLine DAY) end_date, A.s_public,\n" +
                "D.g_sample_num, D.g_deadline FROM survey A, members C, grades D WHERE A.email = C.email AND C.g_name = D.g_name) sub1 \n" +
                "LEFT JOIN (SELECT s_code, COUNT(s_code) interest_count FROM interests GROUP BY s_code) sub2 \n" +
                "ON (sub1.s_code = sub2.s_code) LEFT join (SELECT s_code, COUNT(s_code) sample_num from pointhistory\n" +
                "WHERE ph_type='P' GROUP by s_code) sub3 ON (sub1.s_code = sub3.s_code)\n" +
                "where sub1.s_reported != 'Y' AND g_sample_num - IFNULL(sample_num, 0) != 0 \n" +
                "AND DATE(DATE_ADD(sub1.writtendate, INTERVAL sub1.g_deadLine DAY)) > DATE(NOW()) AND sub1.email = ?\n" +
                "ORDER by TIMESTAMPDIFF(DAY, NOW(), end_date) desc limit ?, ?;";

        public static final String SURVEY_ENDED_INFO_BY_EMAIL = "select sub1.s_code s_code, nick writer, s_title, IFNULL(interest_count, 0), DATE(writtendate) written_date, \n" +
                "DATE(end_date) end_date FROM (SELECT A.s_code, A.email, C.nick, A.s_title, A.writtendate, \n" +
                "A.s_reported, DATE_ADD(A.writtendate, INTERVAL D.g_deadLine DAY) end_date, A.s_public,\n" +
                "D.g_sample_num, D.g_deadline FROM survey A, members C, grades D \n" +
                "WHERE A.email = C.email AND C.g_name = D.g_name) sub1 LEFT JOIN \n" +
                "(SELECT s_code, COUNT(s_code) interest_count FROM interests GROUP BY s_code) sub2 \n" +
                "ON (sub1.s_code = sub2.s_code) LEFT join (select s_code, COUNT(s_code) sample_num\n" +
                "FROM pointhistory WHERE ph_type='P' GROUP by s_code) sub3 ON (sub1.s_code = sub3.s_code)\n" +
                "where g_sample_num - IFNULL(sample_num, 0) = 0 OR DATE(NOW()) >= DATE(end_date)\n" +
                "AND sub1.email = ? AND sub1.s_reported != 'Y'\n" +
                "ORDER BY TIMESTAMPDIFF(DAY, NOW(), end_date) ASC limit ?, ?;";

        public static final String SURVEY_INTEREST_CURRENT_INFO_BY_EMAIL = "SELECT sub1.s_code s_code, nick writer, s_title, IFNULL(interest_count,0), DATE(writtendate) written_date, \n" +
                "DATE(end_date) end_date FROM (SELECT A.s_code, A.email, C.nick, A.s_title, A.writtendate, \n" +
                "A.s_reported, DATE_ADD(A.writtendate, INTERVAL D.g_deadLine DAY) end_date, A.s_public,\n" +
                "D.g_sample_num, D.g_deadline FROM survey A, members C, grades D \n" +
                "WHERE A.email = C.email AND C.g_name = D.g_name) sub1 LEFT JOIN (SELECT s_code, \n" +
                "COUNT(s_code) interest_count FROM interests GROUP BY s_code) sub2 ON (sub1.s_code = sub2.s_code)\n" +
                "LEFT join (SELECT s_code, COUNT(s_code) sample_num from pointhistory WHERE ph_type='P'\n" +
                "GROUP by s_code) sub3 ON (sub1.s_code = sub3.s_code) INNER JOIN \n" +
                "(SELECT\tA.email, A.s_code FROM interests A, survey B where A.s_code = B.s_code \n" +
                "AND A.email = ?) sub4 ON (sub4.s_code = sub1.s_code) where sub1.s_reported != 'Y'\n" +
                "AND g_sample_num - IFNULL(sample_num, 0) != 0 \n" +
                "AND DATE(DATE_ADD(sub1.writtendate, INTERVAL sub1.g_deadLine DAY)) > DATE(NOW())\n" +
                "ORDER by TIMESTAMPDIFF(DAY, NOW(), end_date) ASC limit ?, ?;";

        public static final String SURVEY_INTEREST_ENDED_INFO_BY_EMAIL = "SELECT sub1.s_code s_code, nick writer, s_title, IFNULL(interest_count,0), DATE(writtendate) written_date, \n" +
                "DATE(end_date) end_date FROM (SELECT A.s_code, A.email, C.nick, A.s_title, A.writtendate, \n" +
                "A.s_reported, DATE_ADD(A.writtendate, INTERVAL D.g_deadLine DAY) end_date, A.s_public,\n" +
                "D.g_sample_num, D.g_deadline FROM survey A, members C, grades D \n" +
                "WHERE A.email = C.email AND C.g_name = D.g_name) sub1 LEFT JOIN \n" +
                "(SELECT s_code, COUNT(s_code) interest_count FROM interests GROUP BY s_code) sub2 \n" +
                "ON (sub1.s_code = sub2.s_code) LEFT join (SELECT s_code, COUNT(s_code) sample_num\n" +
                "from pointhistory WHERE ph_type='P' GROUP by s_code) sub3 ON (sub1.s_code = sub3.s_code)\n" +
                "INNER JOIN (select A.email, A.s_code FROM interests A, survey B where A.s_code = B.s_code \n" +
                "AND A.email = ?) sub4 ON (sub4.s_code = sub1.s_code) WHERE sub1.s_public = 'Y' \n" +
                "AND sub1.s_reported != 'Y' AND (g_sample_num - IFNULL(sample_num, 0) = 0\n" +
                "OR DATE(NOW()) >= DATE(end_date)) ORDER by TIMESTAMPDIFF(DAY, NOW(), end_date) ASC limit ?,?;";

        public static final String SURVEY_PURCHASED_INFO_BY_EMAIL = "SELECT sub1.s_code s_code, nick writer, s_title, IFNULL(interest_count,0), DATE(writtendate) written_date, \n" +
                "DATE(end_date) end_date FROM (SELECT A.s_code, A.email, C.nick, A.s_title, A.writtendate, \n" +
                "A.s_reported, DATE_ADD(A.writtendate, INTERVAL D.g_deadLine DAY) end_date, A.s_public,\n" +
                "D.g_sample_num, D.g_deadline FROM survey A, members C, grades D \n" +
                "WHERE A.email = C.email AND C.g_name = D.g_name) sub1 LEFT JOIN \n" +
                "(SELECT s_code, COUNT(s_code) interest_count FROM interests GROUP BY s_code) sub2 \n" +
                "ON (sub1.s_code = sub2.s_code) LEFT join (SELECT s_code, COUNT(s_code) sample_num\n" +
                "from pointhistory WHERE ph_type='P' GROUP by s_code) sub3 ON (sub1.s_code = sub3.s_code)\n" +
                "INNER JOIN (SELECT s_code FROM pointhistory WHERE email=? AND ph_type='B') sub4 \n" +
                "ON (sub4.s_code = sub1.s_code) WHERE sub1.s_reported != 'Y'\n" +
                "ORDER by TIMESTAMPDIFF(DAY, NOW(), end_date) DESC limit ?, ?;";

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

        public static final String SURVEY_LIST_BY_CATEGORY = "SELECT sub1.s_code s_code, nick writer, s_title, IFNULL(interest_count, 0), CAST(writtendate AS DATE) written_date\n" +
                "FROM (SELECT A.s_code, B.nick, A.s_title, A.writtendate, A.s_reported, A.s_id, C.g_deadline, C.g_sample_num,\n" +
                "DATE_ADD(A.writtendate, INTERVAL C.g_deadLine DAY) end_date\t\n" +
                "FROM survey A, members B, grades C WHERE A.email = B.email AND A.c_code=? AND B.g_name = C.g_name) sub1\n" +
                "LEFT JOIN (SELECT s_code, COUNT(s_code) interest_count FROM interests GROUP BY s_code) sub2 ON(sub1.s_code = sub2.s_code) \n" +
                "LEFT join (SELECT s_code, COUNT(s_code) sample_num from pointhistory WHERE ph_type='P'\n" +
                "GROUP by s_code) sub3 ON (sub1.s_code = sub3.s_code) where sub1.s_reported != 'Y'\n" +
                "AND g_sample_num - IFNULL(sample_num, 0) != 0 AND DATE(DATE_ADD(sub1.writtendate, INTERVAL sub1.g_deadLine DAY)) > DATE(NOW()) \n" +
                "ORDER by TIMESTAMPDIFF(DAY, NOW(), end_date) asc limit ?, ?;";

        public static final String TOTAL_COUNT_OF_CURRENT_SURVEY_BY_EMAIL = "select COUNT(*) FROM (select A.s_code, A.email, A.writtendate, A.s_reported, DATE_ADD(A.writtendate, INTERVAL D.g_deadLine DAY) end_date,\n" +
                "D.g_sample_num, D.g_deadline FROM survey A, members C, grades D WHERE A.email = C.email AND C.g_name = D.g_name) sub1\n" +
                "LEFT join (SELECT s_code FROM interests GROUP BY s_code) sub2 ON (sub1.s_code = sub2.s_code) \n" +
                "LEFT join (select s_code, COUNT(s_code) sample_num from pointhistory WHERE ph_type='P' GROUP by s_code) sub3 ON (sub1.s_code = sub3.s_code)\n" +
                "where sub1.s_reported != 'Y' AND g_sample_num - IFNULL(sample_num, 0) != 0 AND DATE(DATE_ADD(sub1.writtendate, INTERVAL sub1.g_deadLine DAY)) > DATE(NOW())\n" +
                "AND sub1.email = ?;";

        public static final String TOTAL_COUNT_OF_ENDED_SURVEY_BY_EMAIL = "SELECT COUNT(*) FROM (SELECT A.s_code, A.email, A.s_reported, DATE_ADD(A.writtendate, INTERVAL D.g_deadLine DAY) end_date, \n" +
                "D.g_sample_num, D.g_deadline FROM survey A, members C, grades D WHERE A.email = C.email AND C.g_name = D.g_name) sub1 \n" +
                "LEFT JOIN (SELECT s_code, COUNT(s_code) interest_count FROM interests GROUP BY s_code) sub2 ON (sub1.s_code = sub2.s_code)\n" +
                "LEFT join (select s_code, COUNT(s_code) sample_num FROM pointhistory WHERE ph_type='P'\n" +
                "GROUP BY s_code) sub3 ON (sub1.s_code = sub3.s_code)\n" +
                "where g_sample_num - IFNULL(sample_num, 0) = 0 OR DATE(NOW()) >= DATE(end_date)\n" +
                "AND sub1.email = ? AND sub1.s_reported != 'Y'\n" +
                "ORDER by TIMESTAMPDIFF(DAY, NOW(), end_date) ASC;";
    }

    public static class Members {

        public static final String MEMBER_PASS_UPDATE = "UPDATE members SET pass=PASSWORD(?) WHERE email=?";
        public static final String MEMBER_SIGNUP = "INSERT INTO members VALUES(?, PASSWORD(?), ?, DEFAULT, 'IRON')";
        public static final String MEMBER_POINT = "SELECT IFNULL(SUM(p.pointchange), 0) FROM members m NATURAL JOIN pointhistory p WHERE m.email = ?";
        public static final String GET_ALL_EMAILS = "SELECT email FROM members";
        public static final String UPDATE_GRADE_BY_EMAIL = "UPDATE members SET g_name=? WHERE email=?";
        public static final String GET_POINT_BY_GRADE = "SELECT g_point FROM grades WHERE g_name=?";
    }

    public static class Survey {

        public static final String SURVEY_HISTORY_BY_CODE = "SELECT s_code, s_title, email, c_code FROM survey WHERE s_code=?";
        public static final String SURVEY_WRITE_COUNT = "SELECT COUNT(*) FROM survey WHERE email=?";
        public static final String SURVEY_PARTICIPATE_COUNT = "SELECT COUNT(*) FROM pointhistory WHERE email=? AND ph_type='P'";
        public static final String SURVEY_LAST_S_ID = "SELECT s_id FROM survey WHERE email=? AND s_reported != 'Y' ORDER BY s_code DESC LIMIT 1;";
    }

    public static class PointHistory {
        public static final String GET_POINT_BY_EMAIL = "SELECT SUM(B.pointchange) AS point FROM members AS A NATURAL JOIN pointhistory AS B WHERE A.email = ?";
        public static final String GET_SAMPLE_COUNT_BY_S_CODE = "SELECT count(*) FROM pointhistory WHERE s_code =? AND pointchange = 5 AND ph_type='P'";
        public static final String GET_TOTAL_COUNT_OF_PURCHASE_BY_EMAIL = "SELECT count(*) FROM pointhistory WHERE email=? AND ph_type='B'";
    }

    public static class Category {
        public static final String GET_DESC_BY_CODE = "SELECT c_desc FROM categories WHERE c_code = ?";
    }

    public static class Interest {
        public static final String GET_COUNT_BY_S_CODE = "SELECT COUNT(*) FROM interests WHERE s_code=?";
        public static final String TOTAL_COUNT_OF_INTERESTS_BY_EMAIL = "SELECT COUNT(*) FROM (SELECT A.s_code, DATE_ADD(A.writtendate, INTERVAL D.g_deadLine DAY) end_date,\n" +
                "A.s_public,\tD.g_sample_num, D.g_deadline, A.writtendate, A.s_reported FROM survey A, members C, grades D \n" +
                "WHERE A.email = C.email AND C.g_name = D.g_name) sub1 LEFT JOIN (SELECT s_code, COUNT(s_code) interest_count FROM interests \n" +
                "GROUP BY s_code) sub2 ON (sub1.s_code = sub2.s_code) LEFT join (SELECT s_code, COUNT(s_code) sample_num FROM pointhistory\n" +
                "WHERE ph_type='P' GROUP BY s_code) sub3 ON (sub1.s_code = sub3.s_code) INNER JOIN (SELECT A.email, A.s_code \n" +
                "FROM interests A, survey B WHERE A.s_code = B.s_code AND A.email = 'robbyra@gmail.com') sub4 ON (sub4.s_code = sub1.s_code)\n" +
                "WHERE g_sample_num - IFNULL(sample_num, 0) != 0 AND DATE(DATE_ADD(sub1.writtendate, INTERVAL sub1.g_deadLine DAY)) > DATE(NOW())\n" +
                "AND sub1.s_reported != 'Y';";

        public static final String TOTAL_COUNT_OF_ENDED_INTEREST_SURVEYS = "SELECT COUNT(*) FROM (SELECT A.s_code, DATE_ADD(A.writtendate, INTERVAL D.g_deadLine DAY) end_date,\n" +
                "A.s_public, A.s_reported, D.g_sample_num, D.g_deadline FROM survey A, members C, grades D \n" +
                "WHERE A.email = C.email AND C.g_name = D.g_name) sub1 LEFT JOIN (SELECT s_code, COUNT(s_code) interest_count \n" +
                "FROM interests GROUP BY s_code) sub2 ON (sub1.s_code = sub2.s_code)\n" +
                "LEFT join (SELECT s_code, COUNT(s_code) sample_num FROM pointhistory WHERE ph_type='P'\n" +
                "GROUP BY s_code) sub3 ON (sub1.s_code = sub3.s_code) INNER JOIN \n" +
                "(select A.email, A.s_code FROM interests A, survey B WHERE A.s_code = B.s_code \n" +
                "AND A.email = ?) sub4 ON (sub4.s_code = sub1.s_code) WHERE sub1.s_public = 'Y'\n" +
                "AND sub1.s_reported != 'Y'AND (g_sample_num - IFNULL(sample_num, 0) = 0\n" +
                "OR DATE(NOW()) >= DATE(end_date));";
    }

    public static class Choices {
        public static final String GET_CHOICE_NUMBERS_BY_S_CODE_AND_Q_NUMBER = "SELECT choice_num FROM choices WHERE s_code = ? AND q_number = ?;";
    }

    public static class ChoiceResults {
        public static final String GET_CHOICE_COUNTS_BY_S_CODE_AND_Q_NUMBER_AND_CHOICE_NUM = "SELECT COUNT(*) FROM choiceresults WHERE s_code=? AND q_number=? AND choice_num=?";
    }

    public static class SubjectiveResults{
        public static final String GET_ANSWERS_BY_S_CODE_AND_Q_NUMBER = "SELECT answer FROM subjectiveresults WHERE s_code=? AND q_number=?";
    }

    public static class Reports {
        public static final String REMOVE_REPORT_BY_S_CODE = "DELETE FROM reports WHERE s_code=?";
        public static final String GET_ALL_REPORTED_S_CODE = "SELECT s_code FROM reports WHERE r_state='Y'";
    }
}
