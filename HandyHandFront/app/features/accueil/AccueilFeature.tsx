import React, { useState, useEffect } from "react";
import { Form } from "react-bootstrap";
import ContentPage from "../../containers/ContentPage";




export default function AccueilFeature() {

  const [seconds, setSeconds] = useState(0);

  function getCurrentImage() : JSX.Element {
    return <img src={`http://localhost:8080/leap/view?rdm=${seconds}`} alt={`Img ${seconds}`} height={100} width={100}/>;
  };

  useEffect(() => {
    const interval = setInterval(() => {
      console.log(seconds);
      setSeconds(seconds => seconds+1);
    }, 500);
    return () => clearInterval(interval);
  }, [seconds]);

  return (
    <ContentPage>
      <div>
        <Form>
          <Form.Check
            type="switch"
            id="custom-switch"
            label="actived"
          />
        </Form>
        { getCurrentImage() }
      </div>
    </ContentPage>
  );
 }
