import React from 'react';
import { Link } from 'react-router-dom';
import { Button, Form, Row, Container } from 'react-bootstrap';
import { useForm } from 'react-hook-form';
import routes from '../../constants/routes.json';
import ContentPage from '../../containers/ContentPage';
import { NewScript } from '../../utils/HandyHandAPI/HandyHandAPIType';
import HandyHandAPI from '../../utils/HandyHandAPI/HandyHandAPI';

interface FormElements {
  file: FileList;
  description: string;
}

export default function AjouterScriptFeature() {
  const { register, handleSubmit, errors } = useForm<FormElements>();

  const onSubmit = (data: FormElements) => {
    const file: File | null = data.file.item(0);
    if (file == null) {
      throw new Error('No file found');
    }
    const returnData: NewScript = {
      file: file.path,
      description: data.description,
      execType: file.type,
      args: [],
    };

    console.log(returnData);
    new HandyHandAPI()
      .addNewScript(returnData)
      .then((r) => console.log(r))
      .catch((err) => console.log(err));
  };

  return (
    <ContentPage>
      <Container>
        <Row>
          <Form onSubmit={handleSubmit(onSubmit)}>
            <Form.Group controlId="formGridAddress1">
              <Form.Label>Nom du script</Form.Label>
              <Form.Control placeholder="..." />
            </Form.Group>

            <Form.Group controlId="formGridAddress1">
              <Form.Label>Description</Form.Label>
              <Form.Control
                as="textarea"
                rows={3}
                id="description"
                name="description"
                ref={register}
              />
            </Form.Group>

            <Form.Group>
              <Form.File
                className="position-relative"
                required
                label="Choisissez un script"
                feedbackTooltip
                id="file"
                name="file"
                ref={register({ required: true })}
              />
              { errors.file && errors.file.type === "required" && (
                <div className="error">The file is mandatory</div>
              )}
            </Form.Group>
            <Button type="submit" variant="primary">SUBMIT</Button>
            <Link to={routes.COUNTER}>
              <Button variant="primary">Valider</Button>
            </Link>
          </Form>
        </Row>
      </Container>
    </ContentPage>
  );
}
