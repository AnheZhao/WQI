package org.whois.dao;

import org.whois.dao.basic.BaseDao;
import org.whois.model.WhoisQueryRecord;

public class WQRDao extends BaseDao {

	public int addWQR(WhoisQueryRecord wqr) {
		String sql = "insert into whois_query_record(wqr_domain,wqr_prefix,wqr_Suffix,wqr_useTime,wqr_ipAddress)"
				+ " values(?,?,?,?,?)";
		Object[] args = { wqr.getWqrDomain(), wqr.getWqrPrefix(),
				wqr.getWqrSuffix(), wqr.getWqrUseTime(), wqr.getWqrIpAddress() };
		int rs = executeSQL(sql, args);
		return rs;
	}

}
