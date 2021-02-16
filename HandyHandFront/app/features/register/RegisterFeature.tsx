import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import { useForm } from 'react-hook-form';
import routes from '../../constants/routes.json';
import ContentPage from '../../containers/ContentPage';
import { UserCreds } from '../../utils/HandyHandAPI/HandyHandAPIType';
import HandyHandAPI from '../../utils/HandyHandAPI/HandyHandAPI';

interface FormElements {
  mail: string;
  password: string;
  validation: string;
}

export default function RegisterFeature() {
  const { register, handleSubmit, errors } = useForm<FormElements>();
  const [error, setError] = useState('');
  const history = useHistory();

  const onSubmit = (data: FormElements) => {
    if (data.password !== data.validation) {
      setError("Passwords aren't matching!");
      return;
    }

    const returnData: UserCreds = {
      mail: data.mail,
      password: data.password,
    };

    new HandyHandAPI()
      .createNewUser(returnData)
      .then((r) => {
        if(r?.status != 200){
          setError(r?.error);
          return;
        }
        history.push(routes.CONNECTION);
      })
      .catch((err) => {
        console.log(err);
        setError(err.message);
      });
  };

  return (
    <ContentPage childrenName="Register">
      <div>
        <Form onSubmit={handleSubmit(onSubmit)}>
          <h2>Register</h2>
          <h6 className={error !== '' ? 'text-danger' : ''}>
            {error !== '' ? error : 'Create a new account'}
          </h6>
          <Form.Group controlId="formBasicEmail">
            <Form.Control
              type="email"
              placeholder="Mail"
              id="mail"
              name="mail"
              ref={register({ required: true })}
            />
            {errors.mail && errors.mail.type === 'required' && (
              <div className="error">Your mail is mandatory</div>
            )}
          </Form.Group>

          <Form.Group controlId="formBasicPassword">
            <Form.Control
              type="password"
              placeholder="Password please"
              id="password"
              name="password"
              ref={register({ required: true })}
            />
            {errors.password && errors.password.type === 'required' && (
              <div className="error">Your password is mandatory</div>
            )}
          </Form.Group>

          <Form.Group controlId="formBasicPassword">
            <Form.Control
              type="password"
              placeholder="Password again, just to be sure"
              ref={register({ required: true })}
              id="validation"
              name="validation"
            />
            {errors.validation && errors.validation.type === 'required' && (
              <div className="error">Your validation is mandatory</div>
            )}
          </Form.Group>
          <Button type="submit" variant="primary" block>
            register now!
          </Button>
          <Form.Text className="text-muted">
            If you already have an account,{' '}
            <Link to={routes.CONNECTION}>Click Here !</Link>
          </Form.Text>
        </Form>
      </div>
    </ContentPage>
  );
}
