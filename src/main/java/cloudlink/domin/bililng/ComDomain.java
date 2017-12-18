package cloudlink.domin.bililng;

/**
 * @author taeyeonkim
 *
 * Billing 관련 table 만들 때 필요한 key 또는 변수정보
 */
public class ComDomain {

	/**
	 * TABLE 관련
	 */
	public static final String TABLE_NAME	= "AWSPriceInfo";	// table 이름
	public static final String PRIMARY_KEY 	= "RateCode";		// 생성되는 table에서 primary key를 지정
	public static final String SORT_KEY		= "OfferCode"; 		// index key
	
	/**
	 * 변수
	 */
	public static final String NULL			= "empty";		// 공백을 insert할 때 
	public static final String CSV_KEY		= "SKU";		// csv파일에서 key르 시작되는 행을 뽑아낼 때 구분하는 변수
	public static final String VERSION		= "Version";
}
