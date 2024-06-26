import "./Footer.css";

const Footer = () => {
  return (
    <footer>
      <div className="left-content">
        <img
          className="img_size"
          src="../images/Logo 2-01.png"
          alt="RutaLibre-logo"
        />
        <p>Copyright © 2023 RutaLibre S.A. - Todos los derechos reservados.</p>
      </div>
      <div className="right-content">
        <img
          className="logo"
          src="../images/ico-facebook.png"
          alt="Facebook-logo"
        />
        <img
          className="logo"
          src="../images/ico-instagram.png"
          alt="Instagram-logo"
        />
        <img
          className="logo"
          src="../images/ico-tiktok.png"
          alt="TikTok-logo"
        />
        <img
          className="logo"
          src="../images/ico-whatsapp.png"
          alt="Whatsapp-logo"
        />
      </div>
    </footer>
  );
};
export default Footer;
