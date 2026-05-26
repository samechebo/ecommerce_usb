import { useState, useEffect } from 'react';

export default function Modal({ title, subtitle, fields, onSubmit, onClose, initialData, submitLabel = 'Guardar' }) {
  const [form, setForm] = useState({});

  useEffect(() => {
    const init = {};
    fields.forEach(f => { init[f.name] = initialData?.[f.name] ?? ''; });
    setForm(init);
  }, [fields, initialData]);

  const handle = (name, val) => setForm(p => ({ ...p, [name]: val }));

  const submit = e => {
    e.preventDefault();
    const out = {};
    Object.entries(form).forEach(([k, v]) => {
      if (v === '' || v === null || v === undefined) return;
      if (v === 'true')  { out[k] = true; return; }
      if (v === 'false') { out[k] = false; return; }
      const n = Number(v);
      out[k] = (!isNaN(n) && v !== '') ? n : v;
    });
    onSubmit(out);
  };

  return (
    <div
      style={{ position: 'fixed', inset: 0, background: 'rgba(0,0,0,0.7)', backdropFilter: 'blur(4px)', display: 'flex', alignItems: 'center', justifyContent: 'center', zIndex: 1000, padding: 16 }}
      onClick={e => e.target === e.currentTarget && onClose()}
    >
      <div className="fade-in" style={{ background: 'var(--bg-surface)', border: '1px solid var(--border-default)', borderRadius: 'var(--radius-xl)', width: '100%', maxWidth: 500, maxHeight: '90vh', overflow: 'hidden', display: 'flex', flexDirection: 'column' }}>
        <div style={{ padding: '24px 28px 20px', borderBottom: '1px solid var(--border-subtle)' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
            <div>
              <h3 style={{ fontFamily: 'var(--font-display)', fontSize: 22, fontWeight: 400, color: 'var(--text-primary)', margin: 0 }}>{title}</h3>
              {subtitle && <p style={{ fontSize: 13, color: 'var(--text-muted)', marginTop: 4 }}>{subtitle}</p>}
            </div>
            <button onClick={onClose} style={{ background: 'none', border: 'none', color: 'var(--text-muted)', fontSize: 18, padding: 4, marginLeft: 16, minWidth: 'auto' }}>
              <i className="fas fa-xmark" />
            </button>
          </div>
        </div>

        <form onSubmit={submit} style={{ overflowY: 'auto', flex: 1, padding: '20px 28px' }}>
          <div style={{ display: 'grid', gridTemplateColumns: fields.length > 4 ? '1fr 1fr' : '1fr', gap: 14 }}>
            {fields.map(f => (
              <div key={f.name} style={{ gridColumn: f.wide ? '1 / -1' : 'auto' }}>
                <label style={{ display: 'block', fontSize: 12, fontWeight: 500, color: 'var(--text-muted)', marginBottom: 6, textTransform: 'uppercase', letterSpacing: '0.06em' }}>
                  {f.label} {f.required && <span style={{ color: 'var(--accent)' }}>*</span>}
                </label>
                <input
                  type={f.type || 'text'}
                  value={form[f.name] ?? ''}
                  placeholder={f.placeholder || ''}
                  onChange={e => handle(f.name, e.target.value)}
                  required={f.required}
                />
              </div>
            ))}
          </div>

          <div style={{ display: 'flex', gap: 10, justifyContent: 'flex-end', marginTop: 24, paddingTop: 20, borderTop: '1px solid var(--border-subtle)' }}>
            <button type="button" onClick={onClose}>Cancelar</button>
            <button type="submit" style={{ background: 'var(--accent)', border: '1px solid var(--accent)', color: '#fff' }}>
              <i className="fas fa-check" />
              {submitLabel}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
