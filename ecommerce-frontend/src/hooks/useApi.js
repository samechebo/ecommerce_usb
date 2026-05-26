import { useState, useCallback } from 'react';

const BASE = 'http://localhost:8080';

export function useApi() {
  const [loading, setLoading] = useState(false);
  const [error,   setError]   = useState(null);

  const request = useCallback(async (method, path, body) => {
    setLoading(true);
    setError(null);
    try {
      const opts = {
        method,
        headers: { 'Content-Type': 'application/json' },
      };
      if (body) opts.body = JSON.stringify(body);
      const res  = await fetch(`${BASE}${path}`, opts);
      const text = await res.text();
      const json = text ? JSON.parse(text) : null;
      if (!res.ok) throw new Error(json?.message || `Error ${res.status}`);
      return json;
    } catch (e) {
      setError(e.message);
      throw e;
    } finally {
      setLoading(false);
    }
  }, []);

  const getAll  = useCallback((entity)      => request('GET',    `/${entity}/all`), [request]);
  const getById = useCallback((entity, id)  => request('GET',    `/${entity}/${id}`), [request]);
  const create  = useCallback((entity, data) => request('POST',   `/${entity}`, data), [request]);
  const update  = useCallback((entity, id, data) => request('PUT', `/${entity}/${id}`, data), [request]);
  const remove  = useCallback((entity, id)  => request('DELETE', `/${entity}/${id}`), [request]);

  return { loading, error, getAll, getById, create, update, remove };
}
