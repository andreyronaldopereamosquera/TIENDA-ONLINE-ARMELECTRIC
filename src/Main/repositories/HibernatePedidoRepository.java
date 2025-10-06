package Main.repositories;

import org.hibernate.Session;
import org.hibernate.Transaction;

import Main.entities.Pedido;
import Main.utils.HibernateUtil;
import entities.Cliente;

import java.util.List;

public class HibernatePedidoRepository implements PedidoRepository {
    public void persist(Pedido pedido) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(pedido);
            tx.commit();
            System.out.println("Pedido guardado en BD con ID: " + pedido.getId());
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void save(Pedido pedido) {
    
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public List<Pedido> findByCliente(Cliente cliente) {
        
        throw new UnsupportedOperationException("Unimplemented method 'findByCliente'");
    }

    @Override
    public List<Pedido> findByCliente(Main.entities.Cliente cliente) {
        throw new UnsupportedOperationException("Unimplemented method 'findByCliente'");
    }

    @Override
    public void persist(entities.Pedido pedido) { 
        throw new UnsupportedOperationException("Unimplemented method 'persist'");
    }
}