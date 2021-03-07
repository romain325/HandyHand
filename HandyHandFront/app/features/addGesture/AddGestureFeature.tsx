import React, { useState } from 'react';
import { useHistory } from 'react-router-dom';
import { Button, Form, Row, Container, Modal } from 'react-bootstrap';
import { useForm } from 'react-hook-form';
import ContentPage from '../../containers/ContentPage';
import { GestureCard } from '../../utils/HandyHandAPI/HandyHandAPIType';
import HandyHandAPI from '../../utils/HandyHandAPI/HandyHandAPI';
import routes from '../../constants/routes.json';

interface FormElements {
  name: string;
  description: string;
  double: boolean;
  distance: boolean;
}

export default function AddGestureFeature() {
  const { register, handleSubmit, errors } = useForm<FormElements>();
  const [showModal, setShowModal] = useState(false);
  const [message, setMessage] = useState(
    'Place your hand over the leapmotion and wait please.'
  );
  const history = useHistory();

  const onSubmit = (data: FormElements) => {
    const returnData: GestureCard = {
      name: data.name,
      description: data.description,
      isDoubleHand: data.double,
      isDistanceImportant: data.distance,
      id: '',
    };
    setShowModal(true);
    setTimeout(() => {
      new HandyHandAPI()
        .addNewGesture(returnData)
        .then((r) => {
          if(r.status != 200){
            throw r.text();
          }
          history.push(routes.GESTURE);
        })
        .catch(async (err) => setMessage(await err));
    }, 2000);
  };

  return (
    <ContentPage childrenName="Add Gesture">
      <Container>
        <Row>
          <Form onSubmit={handleSubmit(onSubmit)}>
            <Form.Group>
              <Form.Label>Name</Form.Label>
              <Form.Control
                placeholder="Gesture Name"
                type="text"
                id="name"
                name="name"
                ref={register({ required: true })}
              />
              {errors.name && errors.name.type === 'required' && (
                <div className="error">The name is mandatory</div>
              )}
            </Form.Group>

            <Form.Group>
              <Form.Label>Description</Form.Label>
              <Form.Control
                as="textarea"
                rows={3}
                id="description"
                name="description"
                ref={register({ required: true })}
              />
              {errors.description && errors.description.type === 'required' && (
                <div className="error">The Description is mandatory</div>
              )}
            </Form.Group>

            <Form.Group>
              <Form.Check
                type="checkbox"
                label="Is double handed ?"
                id="double"
                name="double"
                ref={register}
              />
            </Form.Group>

            <Form.Group>
              <Form.Check
                type="checkbox"
                label="Does distance matter ?"
                id="distance"
                name="distance"
                ref={register}
              />
            </Form.Group>

            <Button type="submit" variant="primary">
              SUBMIT
            </Button>
          </Form>
        </Row>

        <Modal show={showModal} onHide={() => setShowModal(false)}>
          <Modal.Header>
            <Modal.Title>{message}</Modal.Title>
          </Modal.Header>
        </Modal>
      </Container>
    </ContentPage>
  );
}
