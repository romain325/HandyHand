import React, { useEffect, useState } from 'react';
import { ScriptCard } from '../../utils/HandyHandAPIType';
import HandyHandAPI from '../../utils/HandyHandAPI';
import ContentPage from '../../containers/ContentPage';

export default function Counter() {
  const [isLoaded, setIsLoaded] = useState(false);
  const [items, setItems] = useState<ScriptCard[]>([]);

  useEffect(() => {
    new HandyHandAPI().getScriptCards().then((data) => {
      setItems(data);
      setIsLoaded(true);
    });
  }, []);

  if (!isLoaded) {
    return <div>Loading</div>;
  }

  return (
    <ContentPage>

    </ContentPage>
  );
}
