package comp3111.coursescraper;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.DomText;
import java.util.Vector;

import org.apache.http.client.HttpResponseException;

/*
 * WebScraper provide a sample code that scrape web content. After it is
 * constructed, you can call the method scrape with a keyword, the client will
 * go to the default url and parse the page by looking at the HTML DOM. <br>
 * In this particular sample code, it access to HKUST class schedule and quota
 * page (COMP). <br>
 * https://w5.ab.ust.hk/wcq/cgi-bin/1830/subject/COMP <br>
 * where 1830 means the third spring term of the academic year 2018-19 and COMP
 * is the course code begins with COMP. <br>
 * Assume you are working on Chrome, paste the url into your browser and press
 * F12 to load the source code of the HTML. You might be freak out if you have
 * never seen a HTML source code before. Keep calm and move on. Press
 * Ctrl-Shift-C (or CMD-Shift-C if you got a mac) and move your mouse cursor
 * around, different part of the HTML code and the corresponding the HTML
 * objects will be highlighted. Explore your HTML page from body &rarr; div
 * id="classes" &rarr; div class="course" &rarr;. You might see something like
 * this: <br>
 * 
 * <pre>
 * {@code
 * <div class="course">
 * <div class="courseanchor" style=
 *"position: relative; float: left; visibility: hidden; top: -164px;"><a name=
 *"COMP1001">&nbsp;</a></div>
 * <div class="courseinfo">
 * <div class="popup attrword"><span class=
 *"crseattrword">[3Y10]</span><div class=
 *"popupdetail">CC for 3Y 2010 &amp; 2011 cohorts</div></div><div class=
 *"popup attrword"><span class="crseattrword">[3Y12]</span><div class=
 *"popupdetail">CC for 3Y 2012 cohort</div></div><div class=
 *"popup attrword"><span class="crseattrword">[4Y]</span><div class=
 *"popupdetail">CC for 4Y 2012 and after</div></div><div class=
 *"popup attrword"><span class="crseattrword">[DELI]</span><div class=
 *"popupdetail">Mode of Delivery</div></div>	
 *    <div class="courseattr popup">
 * 	    <span style=
 *"font-size: 12px; color: #688; font-weight: bold;">COURSE INFO</span>
 * 	    <div class="popupdetail">
 * 	    <table width="400">
 *         <tbody>
 *             <tr><th>ATTRIBUTES</th><td>Common Core (S&amp;T) for 2010 &amp; 2011 3Y programs<br>Common Core (S&amp;T) for 2012 3Y programs<br>Common Core (S&amp;T) for 4Y programs<br>[BLD] Blended learning</td></tr><tr><th>EXCLUSION</th><td>ISOM 2010, any COMP courses of 2000-level or above</td></tr><tr><th>DESCRIPTION</th><td>This course is an introduction to computers and computing tools. It introduces the organization and basic working mechanism of a computer system, including the development of the trend of modern computer system. It covers the fundamentals of computer hardware design and software application development. The course emphasizes the application of the state-of-the-art software tools to solve problems and present solutions via a range of skills related to multimedia and internet computing tools such as internet, e-mail, WWW, webpage design, computer animation, spread sheet charts/figures, presentations with graphics and animations, etc. The course also covers business, accessibility, and relevant security issues in the use of computers and Internet.</td>
 *             </tr>	
 *          </tbody>
 *      </table>
 * 	    </div>
 *    </div>
 * </div>
 *  <h2>COMP 1001 - Exploring Multimedia and Internet Computing (3 units)</h2>
 *  <table class="sections" width="1012">
 *   <tbody>
 *    <tr>
 *        <th width="85">Section</th><th width="190" style=
 *"text-align: left">Date &amp; Time</th><th width="160" style=
 *"text-align: left">Room</th><th width="190" style=
 *"text-align: left">Instructor</th><th width="45">Quota</th><th width=
 *"45">Enrol</th><th width="45">Avail</th><th width="45">Wait</th><th width=
 *"81">Remarks</th>
 *    </tr>
 *    <tr class="newsect secteven">
 *        <td align="center">L1 (1765)</td>
 *        <td>We 02:00PM - 03:50PM</td><td>Rm 5620, Lift 31-32 (70)</td><td><a href
 *=
 *"/wcq/cgi-bin/1830/instructor/LEUNG, Wai Ting">LEUNG, Wai Ting</a></td><td align
 *="center">67</td><td align="center">0</td><td align="center">67</td><td align=
 *"center">0</td><td align="center">&nbsp;</td></tr><tr class="newsect sectodd">
 *        <td align="center">LA1 (1766)</td>
 *        <td>Tu 09:00AM - 10:50AM</td><td>Rm 4210, Lift 19 (67)</td><td><a href=
 *"/wcq/cgi-bin/1830/instructor/LEUNG, Wai Ting">LEUNG, Wai Ting</a></td><td align
 *="center">67</td><td align="center">0</td><td align="center">67</td><td align=
 *"center">0</td><td align="center">&nbsp;</td>
 *    </tr>
 *   </tbody>
 *  </table>
 * </div>
 *}
 * </pre>
 * 
 * <br>
 * The code
 * 
 * <pre>
 * {
 * 	&#64;code
 * 	List<?> items = (List<?>) page.getByXPath("//div[@class='course']");
 * }
 * </pre>
 * 
 * extracts all result-row and stores the corresponding HTML elements to a list
 * called items. Later in the loop it extracts the anchor tag &lsaquo; a
 * &rsaquo; to retrieve the display text (by .asText()) and the link (by
 * .getHrefAttribute()).
 * 
 *
 */
public class Scraper {
	private WebClient client;
	private String title = "";
	private String instructor = "";

	/**
	 * Default Constructor
	 */
	public Scraper() {
		client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
	}

	/**
	 * add the slot to the input section with the info in the input HtmlElement
	 * 
	 * @param e         HtmlElement
	 * @param section   Section type
	 * @param secondRow boolean
	 * @return void
	 */
	private void addSlot(HtmlElement e, Section section, boolean secondRow) {

		/*
		 * for(int i=0; i<6 ;i++) { if (e.getChildNodes().get(i) != null)
		 * System.out.println(i+ e.getChildNodes().get(i).asText()); }
		 * System.out.println();
		 */

		String times[] = e.getChildNodes().get(secondRow ? 0 : 3).asText().split(" ");
		if (!secondRow) {
			title = e.getChildNodes().get(1).asText().split(" ")[0];
			section.setTitle(title);
		}
		if (e.getChildNodes().get(5) != null) {
			instructor = e.getChildNodes().get(5).asText();
//			System.out.println(instructor);
		}
		String venue = e.getChildNodes().get(secondRow ? 1 : 4).asText();

		if (times[0].equals("TBA"))
			return;

		for (int j = 0; j < times[0].length(); j += 2) {
			String code = times[0].substring(j, j + 2);
			if (Slot.DAYS_MAP.get(code) == null)
				break;
			Slot s = new Slot();
			s.setInstructor(instructor);
			s.setDay(Slot.DAYS_MAP.get(code));
			s.setStart(times[1]);
			s.setEnd(times[3]);
			s.setVenue(venue);
			section.addSlot(s);
			section.setInstructor(instructor);
		}

	}

	/**
	 * Return a list of Course class, by web scraping input website with input
	 * (baseurl + "/" + term + "/subject/" + sub)
	 *
	 * @param baseurl an base URL direct to course info
	 * @param term    term code e.g 1910
	 * @param sub     subject codde e.g COMP
	 * @return a list of String storing displaying String (course: XXXX0000 SFQ
	 *         averge score: 99)
	 */
	public List<Course> scrape(String baseurl, String term, String sub) {

		try {

			HtmlPage page = client.getPage(baseurl + "/" + term + "/subject/" + sub);

			// Create a list according the number of course
			List<?> items = (List<?>) page.getByXPath("//div[@class='course']");

			Vector<Course> result = new Vector<Course>();

			for (int i = 0; i < items.size(); i++) {
				Course c = new Course();
				HtmlElement htmlItem = (HtmlElement) items.get(i);

				// get title
				HtmlElement title = (HtmlElement) htmlItem.getFirstByXPath(".//h2");
				c.setTitle(title.asText());
				String coursecode = title.asText().split("\\s+")[0] + title.asText().split("\\s+")[1];
				c.setCourseCode(coursecode);

				List<?> popupdetailslist = (List<?>) htmlItem.getByXPath(".//div[@class='popupdetail']/table/tbody/tr");
				HtmlElement exclusion = null;
				HtmlElement attribute = null;

				// get exclusion courses
				for (HtmlElement e : (List<HtmlElement>) popupdetailslist) {
					HtmlElement t = (HtmlElement) e.getFirstByXPath(".//th"); // EXCLUSION
					HtmlElement d = (HtmlElement) e.getFirstByXPath(".//td"); // EXCLUSION COURSES
					if (t.asText().equals("EXCLUSION")) {
						exclusion = d;
					}
					if (t.asText().equals("ATTRIBUTES")) {
						attribute = d;
					}
				}
				c.setExclusion((exclusion == null ? "null" : exclusion.asText()));
				c.setCC(attribute == null ? false : (attribute.asText().contains("Common Core") ? true : false));

				// Scrape Sections
				List<?> sections = (List<?>) htmlItem.getByXPath(".//tr[contains(@class,'newsect')]");
				for (HtmlElement e : (List<HtmlElement>) sections) {
					Section s = new Section();
					addSlot(e, s, false);
					e = (HtmlElement) e.getNextSibling();
					if (e != null && !e.getAttribute("class").contains("newsect"))
						addSlot(e, s, true);
					s.setCourse(c);
					c.addSection(s);
				}
				result.add(c);
			}
			client.close();
			return result;
		} catch (FailingHttpStatusCodeException httperr) {
			if (httperr.getStatusCode() == 404) {
				ErrorHandling.NotFoundError404();
			}
		} catch (Exception e) {
			ErrorHandling.OtherError(e);
			System.out.println(e);
		}
		return null;
	}

	/**
	 * Return a list of Course class, by web scraping input website with input
	 * (baseurl + "/" + term + "/subject/" + sub)
	 *
	 * @param baseurl an base URL direct to course info
	 * @param term    term code e.g 1910
	 * @param home     subject code e.g COMP
	 * @return subs   vector of String type
	 * @throws Exception default exception
	 */
	public List<String> searchSubject(String baseurl, String term, String home) throws Exception {

		HtmlPage home_page = client.getPage(baseurl + "/" + term + "/subject/" + home);

		// Create a list containing all the subjects HtmlElement
		List<?> subjects = (List<?>) home_page.getByXPath("//div[@class='depts']/a");

		Vector<String> subs = new Vector<String>();

		for (HtmlElement s : (List<HtmlElement>) subjects) {
			String sub = s.asText();
			subs.add(sub);
		}
		client.close();
		return subs;
	}

	/**
	 * Return a list of InsturctorSFQ class, by web scraping input website with
	 * input url
	 *
	 * @param url an absolute URL giving the SFQ info
	 * @return a list of InstructorSFQ class object
	 */
	// task 6 scrape Instructor SFQ
	public List<InstructorSFQ> findInstructorSFQ(String url) {
		try {

			HtmlPage page = client.getPage(url);
			List<InstructorSFQ> Instructors = new ArrayList<InstructorSFQ>();

			List<HtmlTableRow> trlist = page.getByXPath("//tr");
			trlist.remove(0);
			trlist.remove(0);

			for (HtmlTableRow tr : trlist) {
				if (tr.getCell(0).asText().isBlank() && tr.getCell(1).asText().isBlank()) {
					if (tr.getCell(3).asText().split("\\(")[0].equals("-") || tr.getCell(2).asText().isBlank()) {
						continue;
					}
					String name = tr.getCell(2).asText();
					InstructorSFQ existInstructor = checkexist(name, Instructors);
					if (existInstructor != null) {
						existInstructor.addnumofSection();
						existInstructor.addtotalScore(Double.parseDouble(tr.getCell(3).asText().split("\\(")[0]));
//						System.out.println(existInstructor.toString());
					} else {
						InstructorSFQ instr = new InstructorSFQ();
						instr.setname(tr.getCell(2).asText());
						instr.addnumofSection();
						instr.addtotalScore(Double.parseDouble(tr.getCell(3).asText().split("\\(")[0]));
//					System.out.println(instr.toString());
						Instructors.add(instr);
					}
				}
			}
			return Instructors;

		} catch (FailingHttpStatusCodeException httperr) {
			if (httperr.getStatusCode() == 404) {
				ErrorHandling.NotFoundError404();
			}
		} catch (Exception e) {
			ErrorHandling.OtherError(e);
			System.out.println(e);
		}
		return null;
	}

	/**
	 * Check whether name string is in the list
	 *
	 * @param name      an instructor name
	 * @param list  	a list of String storing instructor name
	 * @return the instictorSFQ type instr or null
	 * @throws Exception set error message
	 */
	public InstructorSFQ checkexist(String name, List<InstructorSFQ> list) throws Exception {
		try {
			for (InstructorSFQ instr : list) {
				if (name.equals(instr.getname())) {
					return instr;
				}
			}
		} catch (Exception e) {
			ErrorHandling.OtherError(e);
			System.out.println(e);
		}
		return null;
	}

	/**
	 * Return a list of InsturctorSFQ class, by web scraping input website with
	 * input url
	 *
	 * @param url                 an absolute URL giving the SFQ info
	 * @param enrolled_coursecode a list of String storing enrolled course code
	 * @return a list of String storing displaying String (course: XXXX0000 SFQ
	 *         averge score: 99)
	 */
	// task 6 scrape Course SFQ
	public List<String> findCourseSFQ(String url, List<String> enrolled_coursecode) {
		try {
			if (enrolled_coursecode.isEmpty()) {
				return null;
			}
			HtmlPage page = client.getPage(url);
			List<String> CourseSFQ = new ArrayList<String>();
			Boolean arrivedCourseCode = false;

			for (String code : enrolled_coursecode) {
				int numofsection = 0;
				int i = 0;
				Double score = 0.0;
				DomElement table = page.getElementById(code.substring(0, 4));
				List<HtmlTableRow> trlist = table.getByXPath("//tr");

				for (HtmlTableRow row : trlist) {
					if (row.getCell(0).asText().replaceAll("\\s+", "").equals(code)) {
						arrivedCourseCode = true;
						numofsection = Integer.parseInt(row.getCell(5).asText());
						continue;
					}

					// info with section
					if (arrivedCourseCode && i < numofsection
							&& !row.getCell(1).asText().replaceAll("\\s+", "").isBlank()) {
//						System.out.println(row.getCell(3).asText());
						score += Double.parseDouble(row.getCell(3).asText().split("\\(")[0]);
//						System.out.println(score);
						i++;
						continue;
					}
				}
				CourseSFQ.add("course: " + code + " SFQ averge score: " + score / numofsection);
			}

			return CourseSFQ;
		} catch (

		FailingHttpStatusCodeException httperr) {
			if (httperr.getStatusCode() == 404) {
				ErrorHandling.NotFoundError404();
			}
		} catch (Exception e) {
			ErrorHandling.OtherError(e);
			System.out.println(e);
		}
		return null;
	}

}
