export default function ConfirmDialog({ record, entity, onConfirm, onClose }) {
  return (
    <div
      style={{ position: 'fixed', inset: 0, background: 'rgba(0,0,0,0.7)', backdropFilter: 'blur(4px)', display: 'flex', alignItems: 'center', justifyContent: 'center', zIndex: 1000, padding: 16 }}
      onClick={e => e.target === e.currentTarget && onClose()}
    >
      <div className="fade-in" style={{ background: 'var(--bg-surface)', border: '1px solid var(--border-default)', borderRadius: 'var(--radius-xl)', width: '100%', maxWidth: 400, padding: '28px 28px 24px' }}>
        <div style={{ width: 48, height: 48, borderRadius: 14, background: 'var(--danger-dim)', border: '1px solid rgba(248,113,113,0.25)', display: 'flex', alignItems: 'center', justifyContent: 'center', marginBottom: 16 }}>
          <i className="fas fa-triangle-exclamation" style={{ color: 'var(--danger)', fontSize: 20 }} />
        </div>
        <h3 style={{ fontFamily: 'var(--font-display)', fontSize: 20, fontWeight: 400, margin: '0 0 8px' }}>Eliminar registro</h3>
        <p style={{ fontSize: 14, color: 'var(--text-secondary)', margin: '0 0 8px', lineHeight: 1.6 }}>
          ¿Estás seguro de que quieres eliminar el registro <strong style={{ color: 'var(--text-primary)' }}>#{record?.id}</strong> de <strong style={{ color: 'var(--text-primary)' }}>{entity}</strong>?
        </p>
        <p style={{ fontSize: 12, color: 'var(--text-muted)', margin: '0 0 24px' }}>Esta acción no se puede deshacer.</p>
        <div style={{ display: 'flex', gap: 10, justifyContent: 'flex-end' }}>
          <button onClick={onClose}>Cancelar</button>
          <button onClick={onConfirm} style={{ background: 'var(--danger-dim)', border: '1px solid rgba(248,113,113,0.3)', color: 'var(--danger)' }}>
            <i className="fas fa-trash" /> Eliminar
          </button>
        </div>
      </div>
    </div>
  );
}
