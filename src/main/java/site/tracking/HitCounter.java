package site.tracking;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
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
	private static final long serialVersionUID = 1L;

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
		int hitCount = new HitCountDAO().doHit(counterID);

		OutputStream out = response.getOutputStream();

		BufferedImage hitImage;
		int imageWidth, imageHeight = 30;
		String imageMessage;

		if (hitCount != -1) {
			imageWidth = (int) (20 * Math.log10(hitCount));
			imageMessage = String.valueOf(hitCount);
		} else {
			imageWidth = 100;
			imageMessage = "ERROR";
		}

		hitImage = new BufferedImage(imageWidth, imageHeight,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) hitImage.getGraphics();
		g2.setBackground(Color.WHITE);
		g2.setColor(Color.WHITE);
		g2.clearRect(0, 0, imageWidth, imageHeight);
		g2.drawRect(0, 0, imageWidth, imageHeight);
		g2.setFont(new Font("default", Font.PLAIN, 12));
		g2.setColor(Color.BLACK);
		g2.drawString(imageMessage, 2, 20);

		response.setHeader("Cache-Control",
				"private, no-store, no-cache, must-revalidate");
		response.addHeader("pragma", "no-store,no-cache");
		response.addHeader("cache-control",
				"no-cache, no-store,must-revalidate, max-age=-1");
		response.addHeader("expires", "-1");

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
