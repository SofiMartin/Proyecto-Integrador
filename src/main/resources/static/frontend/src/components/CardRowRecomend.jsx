import CustomCard from "./CustomCard";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

function CardRowReco() {
  const settings = {
    dots: true,
    infinite: true,
    slidesToShow: 5,
    slidesToScroll: 5,
  };

  const imageListRec = [
    "/images/MH1-1.jpg",
    "/images/MH2-1.jpg",
    "/images/MH3-2.jpg",
    "/images/MH3-2.jpg",
    "/images/MH4-1.jpg",
    "/images/MH5-5.jpg",
    "/images/MH6-1.jpg",
    "/images/MH5-5.jpg",
    "/images/MH6-1.jpg",
  ];

  const imageListRandom = Array.from(
    { length: 10 },
    () => imageListRec[Math.floor(Math.random() * imageListRec.length)]
  );

  return (
    <div>
      <h2>Recomendaciones</h2>
      <Slider {...settings}>
        {imageListRandom.map((imageSrc, index) => (
          <div key={index}>
            <CustomCard imageSrc={imageSrc} />
          </div>
        ))}
      </Slider>
    </div>
  );
}
export default CardRowReco;
