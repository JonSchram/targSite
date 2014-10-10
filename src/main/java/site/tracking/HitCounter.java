package site.tracking;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import site.DAO.HitCountDAO;

/**
 * Servlet implementation class HitCounter
 */
@WebServlet("/HitCounter")
public class HitCounter extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -8595069975734076915L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HitCounter() {
	super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
	String counterID = request.getParameter("counterID");

	String increment = request.getParameter("increment");
	boolean doIncrement = true;
	if (increment == null || "false".equals(increment)) {
	    doIncrement = false;
	}

	String invisible = request.getParameter("invisible");
	boolean isInvisible = true;
	if (invisible == null || "false".equals(invisible)) {
	    isInvisible = false;
	}

	int hitCount = new HitCountDAO().doHit(counterID, doIncrement);

	OutputStream out = response.getOutputStream();

	BufferedImage hitImage;
	int imageWidth, imageHeight;
	String imageMessage;
	if (!isInvisible) {
	    imageHeight = 20;
	    if (hitCount != -1) {
		imageMessage = String.valueOf(hitCount);
	    } else {
		imageMessage = "ERROR";
	    }
	    imageWidth = 10 * imageMessage.length();
	} else {
	    imageHeight = 1;
	    imageWidth = 1;
	    imageMessage = "";
	}

	hitImage = new BufferedImage(imageWidth, imageHeight,
		BufferedImage.TYPE_INT_ARGB);
	Graphics2D g2 = (Graphics2D) hitImage.getGraphics();
	if (!isInvisible) {
	    g2.setBackground(Color.WHITE);
	    g2.setColor(Color.WHITE);
	    g2.clearRect(0, 0, imageWidth, imageHeight);
	    g2.drawRect(0, 0, imageWidth, imageHeight);
	    g2.setFont(new Font("default", Font.PLAIN, 14));
	    g2.setColor(Color.BLACK);
	    g2.drawString(imageMessage, 0, 15);
	} else {
	    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
	    g2.setColor(new Color(255, 255, 255, 0));
	    g2.fillRect(0, 0, imageWidth, imageHeight);

	    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
	    g2.fillRect(0, 0, imageWidth, imageHeight);
	}

	response.setHeader("Cache-Control",
		"private, no-store, no-cache, must-revalidate");
	response.addHeader("cache-control",
		"no-cache, no-store,must-revalidate, max-age=-1");
	response.addHeader("pragma", "no-store,no-cache");

	response.addHeader("expires", "-1");

	response.setContentType("image/png");

	ImageIO.write(hitImage, "png", out);

	out.flush();

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
	    HttpServletResponse response) throws ServletException, IOException {
    }

}
