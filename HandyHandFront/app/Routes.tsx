/* eslint react/jsx-props-no-spreading: off */
import React from 'react';
import { Switch, Route } from 'react-router-dom';
import * as routes from './constants/routes.json';
import App from './containers/App';
import HomePage from './containers/HomePage';


// Lazily load routes and code split with webpack
const LazyCounterPage = React.lazy(() =>
  import(/* webpackChunkName: "CounterPage" */ './containers/CounterPage')
);

const CounterPage = (props: Record<string, any>) => (
  <React.Suspense fallback={<h1>Loading...</h1>}>
    <LazyCounterPage {...props} />
  </React.Suspense>
);


export default function Routes() {
  return (
    <App>
      <Switch>
        <Route exact path={routes.HOME} component={HomePage} />
        <Route path={routes.COUNTER} component={CounterPage} />
        <Route path={routes.CONNEXION} component={CounterPage} />
        <Route exact path={routes.SCRIPT} component={CounterPage} />
        <Route path={routes.ADD_SCRIPT} component={CounterPage} />
        <Route path={routes.MY_SCRIPT} component={CounterPage} />
        <Route path={routes.ENREGISTREMENT} component={CounterPage} />
      </Switch>
    </App>
  );
}
