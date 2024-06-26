import { useState } from "react";
import {
  Carousel,
  CarouselItem,
  CarouselControl,
  CarouselIndicators,
  CarouselCaption,
} from "reactstrap";
// import PropTypes from "prop-types";
import "./SliderCardImage.css";

function SliderCardImage(args) {
  // const imagen = [imagenes];

  const items = args.src.map((imagen, index) => ({
    src: `../images/${imagen}`,
    altText: `Slide ${index + 1}`,
    key: index,
  }));

  // const items = [
  //   {
  //     src: "../images/" + imagen[0],
  //     altText: "Motorhome 1",
  //     key: 1,
  //   },
  //   {
  //     src: "../images/" + imagen[1],
  //     altText: "Slide 2",
  //     key: 2,
  //   },
  //   {
  //     src: "../images/" + imagen[2],
  //     altText: "Slide 3",
  //     key: 3,
  //   },
  // ];

  const [activeIndex, setActiveIndex] = useState(0);
  const [animating, setAnimating] = useState(false);

  const next = () => {
    if (animating) return;
    const nextIndex = activeIndex === items.length - 1 ? 0 : activeIndex + 1;
    setActiveIndex(nextIndex);
  };

  const previous = () => {
    if (animating) return;
    const nextIndex = activeIndex === 0 ? items.length - 1 : activeIndex - 1;
    setActiveIndex(nextIndex);
  };

  const goToIndex = (newIndex) => {
    if (animating) return;
    setActiveIndex(newIndex);
  };

  const slides = items.map((item) => {
    return (
      <CarouselItem
        onExiting={() => setAnimating(false)}
        onExited={() => setAnimating(false)}
        key={item.src}
      >
        <img className="carousel-image" src={item.src} alt={item.altText} />
        <CarouselCaption captionText="" captionHeader="" />
      </CarouselItem>
    );
  });

  return (
    <Carousel
      activeIndex={activeIndex}
      next={next}
      previous={previous}
      fade={true}
      {...args}
    >
      <CarouselIndicators
        items={items}
        activeIndex={activeIndex}
        onClickHandler={goToIndex}
      />
      {slides}
      <CarouselControl
        direction="prev"
        directionText="Previous"
        onClickHandler={previous}
      />
      <CarouselControl
        direction="next"
        directionText="Next"
        onClickHandler={next}
      />
    </Carousel>
  );
}

// SliderCardImage.propTypes = {
//   imagenes: PropTypes.oneOfType([
//     PropTypes.string,
//     PropTypes.arrayOf(PropTypes.string),
//   ]).isRequired,
// };

export default SliderCardImage;
