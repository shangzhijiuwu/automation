package apprent.init;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ziroom.utils.PropertiesUtil;
import com.ziroom.utils.ProviderDataSource;

import config.PropertyConstants;
import net.sf.json.JSONObject;
import ziroom.appScript.S_Me;



public class Rent_Login {
private static final Logger logL = Logger.getLogger(Rent_Login.class);
	
	S_Me me = new S_Me();

	@Test(description = "登陆")
	public void Login() {
		// 获取输入sql语句
		String LoginInputSql = PropertiesUtil.getPropValAsString(PropertyConstants.inputOutValueSqlPath,
				"LoginInputSql") + "  caseID='TC000001'";
		// 获取api后缀sql
		String SuffixUrlSql = PropertiesUtil.getPropValAsString(PropertyConstants.inputOutValueSqlPath,
				"SuffixUrlSql") + "  caseID='TC000001'";
		// 获取登陆输入内容
		String LoginInput = ProviderDataSource.getDataString(LoginInputSql);
		// 获取请求http
		String SuffixUrl = ProviderDataSource.getDataString(SuffixUrlSql);
		String domainName = PropertyConstants.INTERFACES_DOMIN;
		String httpUrl = domainName + SuffixUrl;
		// 获取输入字符串 格式化为json
		JSONObject json = JSONObject.fromObject(LoginInput);
		// 请求参数
		String passWord = json.getString("password");
		String loginName = json.getString("login_name");

		JSONObject responseJson = me.s_login(httpUrl, loginName, passWord);

		String actual = "";
		if (responseJson != null) {
			actual = responseJson.getString("status");
			logL.debug("用例login返回值为" + responseJson);
		} else {
			logL.debug("用例login请求返回值------>>>>为空");
		}
		Assert.assertEquals(actual, "success");
	}
	
}
