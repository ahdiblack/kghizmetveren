package com.yaser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.springframework.core.env.Environment;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookLink;
import org.springframework.social.facebook.api.Reference;
import org.springframework.stereotype.Component;

import com.yaser.app.SessionUtil;
import com.yaser.data.dao.ProviderDAO;
import com.yaser.data.dao.TblCategoryDAO;
import com.yaser.data.dao.TblCityDAO;
import com.yaser.data.dao.TblDistrictDAO;
import com.yaser.data.dao.TblPoiAdministratorDAO;
import com.yaser.data.dao.TblPoiDAO;
import com.yaser.data.dao.TblSubdistrictDAO;
import com.yaser.data.model.TblCategory;
import com.yaser.data.model.TblCity;
import com.yaser.data.model.TblDistrict;
import com.yaser.data.model.TblPoi;
import com.yaser.data.model.TblPoiAdministrator;
import com.yaser.data.model.TblSubdistrict;
import com.yaser.jsf.UploadedFile;

@Component
@ManagedBean
@ViewScoped
public class HomeBean {

	private final Facebook facebook;

	private boolean loggedIn = false;

	private List<TblCity> cities;

	private List<TblDistrict> districts;

	private List<TblSubdistrict> subDistricts;

	private List<TblCategory> categories;

	private TblPoi provider = new TblPoi();

	private TblCity selectedCity = new TblCity();

	private TblDistrict selectedDistrict = new TblDistrict();

	private TblSubdistrict selectedSubDistrict = new TblSubdistrict();

	private String selectedId = "";
	private String categoryId = "";
	private String selectedSubDistrictId = "";

	private String lang;
	private String lat;

	@Inject
	SessionUtil sessionUtil;

	@Inject
	ProviderDAO providerDAO;

	@Inject
	TblPoiDAO tblPoiDAO;

	@Inject
	TblPoiAdministratorDAO tblPoiAdministratorDAO;

	@Inject
	TblCityDAO tblCityDAO;

	@Inject
	TblDistrictDAO tblDistrictDAO;

	@Inject
	TblCategoryDAO tblCategoryDAO;

	@Inject
	TblSubdistrictDAO tblSubDistrictDAO;

	private UploadedFile profileImage;

	private String share;

	private String isAuth;
	
	@Inject
	private Environment environment;

	@Inject
	public HomeBean(Facebook facebook) {
		this.facebook = facebook;
	}

	public void saveProvider() {
		if (selectedId.isEmpty() || provider.getPoiName().isEmpty() || provider.getPhone().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Lütfen bütün alanlarý doldurunuz..."));
			return;
		}
		
		provider.setCity(selectedCity);
		
		try {
			changeProvider(provider);
		} catch (UnsupportedEncodingException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Karakter çeviriminde hata..."));
			return;
		}
		
		String poiName = provider.getPoiName().toLowerCase();
		poiName = changeTurtoEng(poiName);
		provider.setUniqueIdentifier(poiName);
		provider.setTblDistrict(selectedDistrict);
		provider.setTblSubdistrict(tblSubDistrictDAO.findOne(Integer
				.parseInt(getSelectedSubDistrictId())));
		if (getProfileImage() != null) {
			provider.setProfileImage(getProfileImage().getFileName());
		}
		provider.setCategory(Integer.valueOf(categoryId));
		provider.setDateAdded(Calendar.getInstance().getTime());
		if (getIsAuth().equals("true")) {
			provider.setAuthorityEmail(sessionUtil.getUser().getEmail());
		}
		// 05322535535
		provider.setCoordLat(new BigDecimal(lat));
		provider.setCoordLong(new BigDecimal(lang));

		tblPoiDAO.save(getProvider());

		TblPoiAdministrator ad = new TblPoiAdministrator();
		ad.setTblPoi(provider);
		ad.setIsPrimary(true);
		ad.setStatus(new Short("1"));
		// ad.setAdministrator_id(sessionUtil.getUser().getSubscriberId());
		ad.setTblSubscriber(sessionUtil.getUser());
		tblPoiAdministratorDAO.save(ad);

		provider = new TblPoi();
		selectedCity = new TblCity();
		selectedDistrict = new TblDistrict();
		selectedSubDistrict = new TblSubdistrict();
		categoryId = "0";
		
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Hizmet veren kaydýnýz oluþturulmuþtur. Teþekkür ederiz." +
						"Þimdi Hesabýnýz bölümünden paylaþýmda bulunabilirsiniz."));
	}

	private void changeProvider(TblPoi provider2) throws UnsupportedEncodingException {
		String address = provider2.getAddress();
		provider2.setAddress(new String(address.getBytes(), "UTF-8"));
		String poiName = provider2.getPoiName();
		provider2.setPoiName(new String (poiName.getBytes(), "UTF-8"));
		String info = provider2.getInfo();
		provider2.setInfo(new String (info.getBytes(), "UTF-8"));
		String website = provider2.getWebsite();
		provider2.setWebsite(new String (website.getBytes(), "UTF-8"));
		String keywords = provider2.getKeywords();
		provider2.setKeywords(new String (keywords.getBytes(), "UTF-8"));
	}

	public void changeDistrict() {
		if (selectedId.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Lütfen il seçin..."));
			return;
		}
		selectedCity = tblCityDAO.findOne(Integer.valueOf(selectedId));
		districts = tblDistrictDAO.getByCityId(selectedCity.getCityId());
		subDistricts = null;
	}

	public void changeSubDistrict() {
		if (selectedId.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Lütfen ilçe seçin..."));
			return;
		}
		selectedDistrict = tblDistrictDAO.findOne(Integer.valueOf(selectedId));
		subDistricts = tblSubDistrictDAO.getByDistrictId(Integer
				.valueOf(selectedId));
	}

	public void shareFacebook() {
		Integer subscriberId = sessionUtil.getUser().getSubscriberId();
		TblPoi p = tblPoiDAO.findByAdminId(subscriberId);
		if (p == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Size ait bir Hizmet Veren kaydý bulunamadý. Lütfen öncelikle Hizmet Veren kaydý girin."));
			return;
		}
		Reference location = facebook.userOperations().getUserProfile()
				.getLocation();
		FacebookLink fl = new FacebookLink(environment.getProperty("appUrl")
				+ p.getUniqueIdentifier(), p.getPoiName(), location.getName(),
				"kimegitsem?com'da tavsiye etmek ister misiniz?");
		facebook.feedOperations().postLink(share, fl);
		share = "";
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Paylaþýmýnýz Facebook duvarýnýza gönderilmiþtir."));
	}

	public boolean findProvider() {
		if (provider == null) {
			Integer subscriberId = sessionUtil.getUser().getSubscriberId();
			provider = tblPoiDAO.findByAdminId(subscriberId);
			
			if (provider == null)
				return false;
			else {
				return true;
			}
		} else {
			return true;
		}
	}
	
	public String changeTurtoEng(String data) {

		char[] arr = data.toCharArray();
		for (char c : arr) {
			switch (c) {
			case 'þ':
			case 'Þ':
				data = data.replace(c, 's');
				break;
			case 'ç':
			case 'Ç':
				data = data.replace(c, 'c');
				break;
			case 'ý':
			case 'Ý':
				data = data.replace(c, 'i');
				break;
			case 'ð':
			case 'Ð':
				data = data.replace(c, 'g');
				break;
			case 'ü':
			case 'Ü':
				data = data.replace(c, 'u');
				break;
			case 'ö':
			case 'Ö':
				data = data.replace(c, 'o');
				break;
			case ' ':
				data = data.replace(c, '_');
				break;
			}
		}
		return data;
	}

	public boolean isLoggedIn() {

		if (!sessionUtil.userSignedIn()) {
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("signin/facebook");
			} catch (IOException e) {

			}
		}

		return loggedIn;
	}

	public List<TblCity> getCities() {

		if (cities == null) {
			cities = tblCityDAO.findAll();
		}

		return cities;
	}

	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public TblPoi getProvider() {
		return provider;
	}

	public void setProvider(TblPoi provider) {
		this.provider = provider;
	}

	public void setCities(List<TblCity> cities) {
		this.cities = cities;
	}

	public TblCity getSelectedCity() {
		return selectedCity;
	}

	public void setSelectedCity(TblCity selectedCity) {
		this.selectedCity = selectedCity;
	}

	public List<TblDistrict> getDistricts() {
		return districts;
	}

	public void setDistricts(List<TblDistrict> districts) {
		this.districts = districts;
	}

	public TblDistrict getSelectedDistrict() {
		return selectedDistrict;
	}

	public void setSelectedDistrict(TblDistrict selectedDistrict) {
		this.selectedDistrict = selectedDistrict;
	}

	public TblSubdistrict getSelectedSubDistrict() {
		return selectedSubDistrict;
	}

	public void setSelectedSubDistrict() {
		selectedSubDistrict = tblSubDistrictDAO.findOne(Integer
				.parseInt(selectedId));
	}

	public List<TblSubdistrict> getSubDistricts() {
		return subDistricts;
	}

	public void setSubDistricts(List<TblSubdistrict> subDistricts) {
		this.subDistricts = subDistricts;
	}

	public String getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}

	public String getSelectedSubDistrictId() {
		return selectedSubDistrictId;
	}

	public void setSelectedSubDistrictId(String selectedSubDistrictId) {
		this.selectedSubDistrictId = selectedSubDistrictId;
	}

	public UploadedFile getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(UploadedFile profileImage) {
		this.profileImage = profileImage;
	}

	public List<TblCategory> getCategories() {
		if (categories == null) {
			categories = tblCategoryDAO.getParentCategories();
		}
		return categories;
	}

	public void setCategories(List<TblCategory> categories) {
		this.categories = categories;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(String isAuth) {
		this.isAuth = isAuth;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	// @RequestMapping(value="/", method=RequestMethod.GET)
	// public String home(Model model) {
	// List<Reference> friends = facebook.friendOperations().getFriends();
	// model.addAttribute("friends", friends);
	// return "home";
	// }

}