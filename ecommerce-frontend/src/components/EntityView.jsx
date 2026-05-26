import { useState, useEffect, useCallback, useRef } from 'react';
import { useApi } from '../hooks/useApi';
import { FIELDS } from '../config/entities';
import DataTable from './DataTable';
import Modal from './Modal';
import ConfirmDialog from './ConfirmDialog';

export default function EntityView({ entity, addToast }) {
  const [data,    setData]    = useState([]);
  const [modal,   setModal]   = useState(null);
  const [confirm, setConfirm] = useState(null);
  const [search,  setSearch]  = useState('');
  const [query,   setQuery]   = useState('');
  const { loading, getAll, getById, create, update, remove } = useApi();
  const inputRef = useRef();

  const fields     = FIELDS[entity.key] || { create: [], update: [] };
  const hasUpdate  = fields.update.length > 0;

  const load = useCallback(async () => {
    try {
      const res = await getAll(entity.key);
      setData(Array.isArray(res) ? res : [res]);
    } catch (e) {
      addToast(e.message, 'error');
      setData([]);
    }
  }, [entity.key, getAll, addToast]);

  useEffect(() => { load(); setSearch(''); setQuery(''); }, [entity.key]);

  const handleSearch = async () => {
    const id = search.trim();
    if (!id) return load();
    try {
      const res = await getById(entity.key, id);
      setData([res]);
      setQuery(id);
    } catch (e) {
      addToast(e.message, 'error');
    }
  };

  const clearSearch = () => { setSearch(''); setQuery(''); load(); };

  const handleCreate = async body => {
    try {
      await create(entity.key, body);
      addToast(`${entity.label} creado exitosamente`, 'success');
      setModal(null);
      load();
    } catch (e) {
      addToast(e.message, 'error');
    }
  };

  const handleUpdate = async body => {
    try {
      await update(entity.key, modal.record.id, body);
      addToast(`${entity.label} actualizado`, 'success');
      setModal(null);
      load();
    } catch (e) {
      addToast(e.message, 'error');
    }
  };

  const handleDelete = async () => {
    try {
      await remove(entity.key, confirm.id);
      addToast(`Registro #${confirm.id} eliminado`, 'success');
      setConfirm(null);
      load();
    } catch (e) {
      addToast(e.message, 'error');
    }
  };

  const filtered = query
    ? data
    : data.filter(row => {
        const q = search.toLowerCase();
        if (!q) return true;
        return Object.values(row).some(v => String(v ?? '').toLowerCase().includes(q));
      });

  return (
    <div className="fade-in" style={{ flex: 1, display: 'flex', flexDirection: 'column', minHeight: 0 }}>
      <div style={{ padding: '32px 36px 0' }}>
        <div style={{ display: 'flex', alignItems: 'flex-start', justifyContent: 'space-between', marginBottom: 24, gap: 16, flexWrap: 'wrap' }}>
          <div>
            <div style={{ display: 'flex', alignItems: 'center', gap: 12, marginBottom: 6 }}>
              <div style={{ width: 40, height: 40, borderRadius: 12, background: `${entity.color}18`, border: `1px solid ${entity.color}33`, display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                <i className={`fas ${entity.icon}`} style={{ fontSize: 16, color: entity.color }} />
              </div>
              <h1 style={{ fontFamily: 'var(--font-display)', fontSize: 28, fontWeight: 400, margin: 0 }}>{entity.label}</h1>
            </div>
            <p style={{ fontSize: 13, color: 'var(--text-muted)', margin: 0 }}>
              {data.length} {data.length === 1 ? 'registro' : 'registros'} · <code style={{ fontSize: 11, background: 'var(--bg-elevated)', padding: '1px 5px', borderRadius: 4, color: 'var(--text-secondary)' }}>/{entity.key}</code>
            </p>
          </div>

          <div style={{ display: 'flex', gap: 8 }}>
            <button onClick={load} style={{ fontSize: 12 }}>
              <i className={`fas fa-rotate-right ${loading ? 'fa-spin' : ''}`} /> Recargar
            </button>
            {fields.create.length > 0 && (
              <button onClick={() => setModal({ type: 'create' })} style={{ background: entity.color, border: `1px solid ${entity.color}`, color: '#fff', fontWeight: 500 }}>
                <i className="fas fa-plus" /> Nuevo
              </button>
            )}
          </div>
        </div>

        <div style={{ display: 'flex', gap: 8, marginBottom: 0, background: 'var(--bg-elevated)', borderRadius: 'var(--radius-md)', border: '1px solid var(--border-subtle)', padding: '10px 14px' }}>
          <i className="fas fa-magnifying-glass" style={{ color: 'var(--text-muted)', fontSize: 14, alignSelf: 'center', flexShrink: 0 }} />
          <input
            ref={inputRef}
            value={search}
            onChange={e => setSearch(e.target.value)}
            onKeyDown={e => e.key === 'Enter' && handleSearch()}
            placeholder={`Buscar en ${entity.label.toLowerCase()}... (Enter para buscar por ID exacto)`}
            style={{ background: 'none', border: 'none', boxShadow: 'none', flex: 1, padding: '0 4px', fontSize: 14 }}
          />
          {search && (
            <button onClick={clearSearch} style={{ background: 'none', border: 'none', color: 'var(--text-muted)', padding: '0 4px', minWidth: 'auto', fontSize: 13 }}>
              <i className="fas fa-xmark" />
            </button>
          )}
          <button onClick={handleSearch} style={{ fontSize: 12, flexShrink: 0 }}>
            Buscar por ID
          </button>
        </div>
      </div>

      <div style={{ flex: 1, overflowY: 'auto', padding: '16px 36px 36px' }}>
        {loading && data.length === 0 ? (
          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', padding: '64px 0', gap: 12, color: 'var(--text-muted)', fontSize: 14 }}>
            <i className="fas fa-circle-notch fa-spin" style={{ fontSize: 20 }} /> Cargando registros...
          </div>
        ) : (
          <div style={{ background: 'var(--bg-surface)', border: '1px solid var(--border-subtle)', borderRadius: 'var(--radius-lg)', overflow: 'hidden' }}>
            {query && (
              <div style={{ padding: '10px 16px', background: 'var(--accent-dim)', borderBottom: '1px solid var(--border-subtle)', display: 'flex', alignItems: 'center', gap: 8 }}>
                <i className="fas fa-filter" style={{ fontSize: 12, color: 'var(--accent-light)' }} />
                <span style={{ fontSize: 12, color: 'var(--accent-light)' }}>Mostrando ID #{query}</span>
                <button onClick={clearSearch} style={{ marginLeft: 'auto', fontSize: 11, padding: '3px 8px', color: 'var(--accent-light)', background: 'transparent', borderColor: 'var(--accent-glow)' }}>
                  Ver todos
                </button>
              </div>
            )}
            <DataTable
              data={filtered}
              hasUpdate={hasUpdate}
              onEdit={record => setModal({ type: 'update', record })}
              onDelete={record => setConfirm({ id: record.id })}
            />
          </div>
        )}
      </div>

      {modal?.type === 'create' && (
        <Modal
          title={`Nuevo registro`}
          subtitle={entity.label}
          fields={fields.create}
          onSubmit={handleCreate}
          onClose={() => setModal(null)}
          submitLabel="Crear"
        />
      )}

      {modal?.type === 'update' && (
        <Modal
          title={`Editar registro #${modal.record.id}`}
          subtitle={entity.label}
          fields={fields.update}
          initialData={modal.record}
          onSubmit={handleUpdate}
          onClose={() => setModal(null)}
          submitLabel="Guardar cambios"
        />
      )}

      {confirm && (
        <ConfirmDialog
          record={confirm}
          entity={entity.label}
          onConfirm={handleDelete}
          onClose={() => setConfirm(null)}
        />
      )}
    </div>
  );
}
