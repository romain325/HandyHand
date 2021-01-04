/* eslint react/jsx-props-no-spreading: off */
import React from 'react';
import { Switch, Route } from 'react-router-dom';
import * as routes from './constants/routes.json';
import App from './containers/App';
import HomePage from './containers/HomePage';
import AjouterScriptFeature from './features/ajouterScript/AjouterScript';
import ConnexionFeature from './features/Connexion/ConnexionFeature';
import EnregistrementFeature from './features/enregistrement/EnregistrementFeature';
import ScriptsFeatures from './features/script/ScriptsFeature';
import SettingsFeature from './features/settings/SettingsFeature';

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
        <Route path={routes.CONNEXION} component={ConnexionFeature} />
        <Route exact path={routes.SCRIPT} component={ScriptsFeatures} />
        <Route path={routes.ADD_SCRIPT} component={AjouterScriptFeature} />
        <Route exact path={routes.MY_SCRIPT} component={EnregistrementFeature} />
        <Route path={routes.ENREGISTREMENT} component={EnregistrementFeature} />
        <Route path={routes.SETTINGS} component={SettingsFeature} />
      </Switch>
    </App>
  );
}
