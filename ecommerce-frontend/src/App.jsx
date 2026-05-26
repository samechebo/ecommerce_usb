import { useState, useCallback } from 'react';
import Sidebar from './components/Sidebar';
import EntityView from './components/EntityView';
import Toast from './components/Toast';
import { ENTITIES } from './config/entities';

let toastId = 0;

export default function App() {
  const [activeKey, setActiveKey] = useState('document-type');
  const [toasts,    setToasts]    = useState([]);

  const addToast = useCallback((message, type = 'info') => {
    const id = ++toastId;
    setToasts(p => [...p, { id, message, type }]);
  }, []);

  const removeToast = useCallback(id => {
    setToasts(p => p.filter(t => t.id !== id));
  }, []);

  const entity = ENTITIES.find(e => e.key === activeKey);

  return (
    <div style={{ display: 'flex', height: '100vh', overflow: 'hidden' }}>
      <Sidebar active={activeKey} setActive={setActiveKey} />

      <div style={{ flex: 1, display: 'flex', flexDirection: 'column', overflow: 'hidden', background: 'var(--bg-base)' }}>
        {entity && (
          <EntityView
            key={activeKey}
            entity={entity}
            addToast={addToast}
          />
        )}
      </div>

      <Toast toasts={toasts} remove={removeToast} />
    </div>
  );
}
